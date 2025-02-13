//
// Created by Daniel on 09-Feb-25.
//

#include "App.h"

#include <iostream>

App::App() {
    window.create(sf::VideoMode::getDesktopMode(), "Compiler", sf::Style::None, sf::State::Fullscreen);
    window.setFramerateLimit(60);
    window.setVerticalSyncEnabled(true);

    if (!font.openFromFile("../../resources/JetBrainsMono-Regular.ttf")) {
        std::cerr << "Failed to load font" << std::endl;
    }

    clock.restart();
}

void App::run() {
    while (window.isOpen()) {
        pollEvents();

        update();

        window.clear(sf::Color::Black);
        draw();
        window.display();
    }
}

void App::pollEvents() {
    while (const std::optional event = window.pollEvent()) {
        if (event->is<sf::Event::Closed>())
            window.close();
        else if (const auto *keyPressed = event->getIf<sf::Event::KeyPressed>()) {
            if (keyPressed->scancode == sf::Keyboard::Scancode::Escape)
                window.close();
            else if (keyPressed->scancode == sf::Keyboard::Scancode::Space) {
                if (keyFrames.empty())
                    return;

                if (!keyFrames[currentKeyFrame]->goToNextFrame())
                    currentKeyFrame++;

                if (currentKeyFrame >= keyFrames.size()) {
                    window.close();
                    return;
                }
            }
        }
    }
}

void App::update() {
    const float dt = clock.restart().asSeconds();

    if (currentKeyFrame >= keyFrames.size())
        return;

    keyFrames[currentKeyFrame]->update(dt);
}

void App::draw() {
    if (currentKeyFrame >= keyFrames.size())
        return;

    keyFrames[currentKeyFrame]->draw(window);
}

void App::addKeyFrame(std::shared_ptr<KeyFrame> keyFrame) {
    keyFrames.push_back(std::move(keyFrame));
}
