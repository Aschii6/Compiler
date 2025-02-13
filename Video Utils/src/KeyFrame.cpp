//
// Created by Daniel on 11-Feb-25.
//

#include "KeyFrame.h"

#include "SFML/Graphics/RenderTarget.hpp"

KeyFrame::KeyFrame(const std::map<unsigned int, std::vector<std::shared_ptr<RenderItem>>> &frame_items): frameItems(frame_items) {
    for (const auto&[fst, snd]: frameItems) {
        if (fst > totalFrames)
            totalFrames = fst;
    }
}

void KeyFrame::setBackgroundImage(std::shared_ptr<sf::Sprite> image) {
    this->backgroundImage = std::move(image);
}

void KeyFrame::draw(sf::RenderTarget &target, const sf::RenderStates &states) const {
    if (backgroundImage.has_value())
        target.draw(*backgroundImage.value(), states);

    for (const auto&[fst, snd]: frameItems) {
        if (fst <= currentFrame) {
            for (const auto &item: snd)
                item->draw(target, states);
        }
    }
}

void KeyFrame::update(const float dt) {
    for (const auto&[fst, snd]: frameItems) {
        if (fst <= currentFrame) {
            for (const auto &item: snd)
                item->update(dt);
        }
    }
}

bool KeyFrame::goToNextFrame() {
    if (currentFrame >= totalFrames)
        return false;

    this->currentFrame++;
    return true;
}
