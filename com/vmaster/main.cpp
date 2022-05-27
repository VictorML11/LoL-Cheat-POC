#include <iostream>
#include <iostream>
#include "kafka/KafkaProducer.h"
#include <string>
#include "nlohmann/json.hpp"
#include "LeagueMemoryReader.h"
#include "Utils.h"
#include "MemSnapshot.h"


using namespace std;
using namespace nlohmann;
using json = nlohmann::json;

int main() {
    printf("[i] Initializing VMaster \n");
    LeagueMemoryReader reader = LeagueMemoryReader();

    MemSnapshot memSnapshot;
    bool rehook = true;
    bool firstIter = true;

    printf("[i] Waiting for league process...\n");

    using namespace kafka::clients;
    std::string brokers = "localhost:9092";
    kafka::Topic topic  = "prueba";

    // Create configuration object
    kafka::Properties props ({
                                     {"bootstrap.servers",  brokers},
                                     //{"enable.idempotence", "true"},
                                     {kafka::clients::producer::Config::ACKS, "1"},
                                     {"compression.type", "lz4"},
                                     {"linger.ms", "100"},
                                     {"batch.size", "100"},
                                     //{"enable.idempotence", "true"},
                             });

    KafkaProducer producer(props);






    while (true) {

        bool isLeagueWindowActive = reader.isLeagueWindowActive();

        try {
            // Try to find the league process and get its information necessary for reading
            if (rehook) {
                reader.hookToProcess();
                rehook = false;
                firstIter = true;
                memSnapshot = MemSnapshot();
                printf("[i] Found league process. Waiting the game to start to start reading memory\n");
            }
            else {

                if (!reader.isHookedToProcess()) {
                    rehook = true;
                    printf("[i] League process is dead.\n");
                    printf("[i] Waiting for league process...\n");
                }
                reader.makeSnapshot(memSnapshot);

                // If the game started
                if (memSnapshot.gameTime > 2.f) {
                    json render;
                    render["gameTime"] = memSnapshot.gameTime;
                    render["width"] = memSnapshot.screenData.width;
                    render["height"] = memSnapshot.screenData.height;
                    render["viewProjMatrix"] = memSnapshot.screenData.viewProjMatrix;

                    string s = render.dump();
                    //printf(">> %s\n", s.c_str());

                    auto record = producer::ProducerRecord(topic,
                                                           kafka::Key("GameRenderer"),
                                                           kafka::Value(s.c_str(), s.size()));

                    // Send the message
                    /*producer.send(record,
                                  [](const producer::RecordMetadata& metadata, const kafka::Error& error) {
                                      if (!error) {
                                          //std::cout << "% Message delivered: " << metadata.toString() << std::endl;
                                      } else {
                                          std::cerr << "% Message delivery failed: " << error.message() << std::endl;
                                      }
                                 },
                                 KafkaProducer::SendOption::ToCopyRecordValue);*/

                    producer.syncSend(record);

                    using clock = std::chrono::system_clock;
                    using ms = std::chrono::duration<double, std::milli>;

                    const auto before = clock::now();

                    json heroArray = json::array();


                    for (auto &item: memSnapshot.heroes){

                        json hero;

                        json positionObj;
                        positionObj["x"] = item.position.x;
                        positionObj["y"] = item.position.y;
                        positionObj["z"] = item.position.z;

                        hero["position"] =  json(positionObj);
                        hero["health"] =  item.health;
                        hero["visible"] =  item.visible;
                        heroArray.push_back(hero);

                    }

                    string heroString = heroArray.dump();
                    //printf(">> %s\n", heroString.c_str());
                    auto heroRecord = producer::ProducerRecord("heroes",
                                                               kafka::Key("Heroe"),
                                                               kafka::Value(heroString.c_str(), heroString.size()));
                    producer.send(heroRecord,
                                  [](const producer::RecordMetadata& metadata, const kafka::Error& error) {
                                      if (!error) {
                                          //std::cout << "% Message delivered: " << metadata.toString() << std::endl;
                                      } else {
                                          std::cerr << "% Message delivery failed: " << error.message() << std::endl;
                                      }
                                  },
                                  KafkaProducer::SendOption::ToCopyRecordValue);
                    //producer.syncSend(heroRecord);
                    const ms duration = clock::now() - before;
                    //std::cout << "It took " << duration.count() << "ms" << std::endl;









                    /*cout << "GameTime " << memSnapshot.gameTime << endl;
                    cout << "Width x h " << memSnapshot.screenData.width << "x" << memSnapshot.screenData.height << endl;
                    cout << "ViewMatrix " << memSnapshot.screenData.viewProjMatrix[0] <<  " - "
                    << memSnapshot.screenData.viewProjMatrix[1] << endl;*/
                }
            }
        }
        catch (WinApiException exception) {
            // This should trigger only when we don't find the league process.
            rehook = true;
        }
        catch (std::runtime_error exception) {
            printf("[!] Unexpected error occured: \n [!] %s \n", exception.what());
            break;
        }

        // render frame
    }


    return 0;
}
