//
// Created by Daniel on 11-Feb-25.
//

#include "SimpleTextItem.h"

#include <iostream>

#include "SFML/Graphics/RenderTarget.hpp"

SimpleTextItem::SimpleTextItem(sf::Text text): text(std::move(text)) {
}

void SimpleTextItem::setPositionChange(sf::Vector2f positionChange, const float duration) {
    if (duration <= 0)
        std::cerr << "Duration must be greater than 0" << std::endl;

    this->positionChange = positionChange;
    this->changeDuration = duration;
}

void SimpleTextItem::draw(sf::RenderTarget &target, const sf::RenderStates states) const {
    target.draw(text, states);
}

void SimpleTextItem::update(const float dt) {
    if (positionChange.has_value()) {
        if (changeTime >= changeDuration)
            return;

        changeTime += dt;
        const sf::Vector2f change = positionChange.value() * (dt / changeDuration);
        text.setPosition(text.getPosition() + change);
    }
}

SimpleTextItem::~SimpleTextItem() = default;
