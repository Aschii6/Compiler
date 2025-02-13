//
// Created by Daniel on 11-Feb-25.
//

#ifndef TYPEWRITERTEXT_H
#define TYPEWRITERTEXT_H
#include "SimpleTextItem.h"


class TypewriterText final : public RenderItem {
    sf::Text text;

    std::string fullText;
    std::string currentText;

    float duration = 0;
    float timeElapsed = 0;
public:
    TypewriterText(const sf::Text &text, float duration);

    void draw(sf::RenderTarget &target, sf::RenderStates states) const override;

    void update(float dt) override;

    ~TypewriterText() override;
};



#endif //TYPEWRITERTEXT_H
