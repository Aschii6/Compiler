//
// Created by Daniel on 11-Feb-25.
//

#ifndef SIMPLETEXTITEM_H
#define SIMPLETEXTITEM_H

#include "RenderItem.h"
#include "SFML/Graphics/Text.hpp"


class SimpleTextItem final : public RenderItem {
    std::optional<sf::Vector2f> positionChange;
    float changeDuration = 0;
    float changeTime = 0;

protected:
    sf::Text text;

public:
    explicit SimpleTextItem(sf::Text text);

    void setPositionChange(sf::Vector2f positionChange, float duration);

    void draw(sf::RenderTarget &target, sf::RenderStates states) const override;

    void update(float dt) override;

    ~SimpleTextItem() override;
};


#endif //SIMPLETEXTITEM_H
