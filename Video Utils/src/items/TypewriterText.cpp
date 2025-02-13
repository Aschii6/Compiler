//
// Created by Daniel on 11-Feb-25.
//

#include "TypewriterText.h"

#include <cmath>
#include <iostream>
#include <ostream>

#include "SFML/Graphics/RenderTarget.hpp"


TypewriterText::TypewriterText(const sf::Text &text, const float duration): text(text) {
    if (duration <= 0)
        std::cerr << "Duration must be greater than 0" << std::endl;

    this->fullText = text.getString();
    this->currentText = "";
    this->duration = duration;

    this->text.setString("");
}

void TypewriterText::draw(sf::RenderTarget &target, const sf::RenderStates states) const {
    target.draw(text, states);
}

void TypewriterText::update(const float dt) {
    if (timeElapsed >= duration)
        return;

    timeElapsed += dt;
    const float progress = timeElapsed / duration;

    const int index = std::floor(progress * static_cast<float>(fullText.size()));
    currentText = fullText.substr(0, index);

    text.setString(currentText);
}

TypewriterText::~TypewriterText() = default;
