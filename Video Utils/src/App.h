//
// Created by Daniel on 09-Feb-25.
//

#ifndef APP_H
#define APP_H

#include <SFML/Graphics.hpp>

#include "KeyFrame.h"

class App {
    sf::RenderWindow window;
    sf::Clock clock;

    sf::Font font;

    std::vector<std::shared_ptr<KeyFrame>> keyFrames;
    unsigned int currentKeyFrame = 0;

    void pollEvents();
    void update();
    void draw();
public:
    App();

    void run();

    void addKeyFrame(std::shared_ptr<KeyFrame> keyFrame);
};



#endif //APP_H
