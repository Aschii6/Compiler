//
// Created by Daniel on 10-Feb-25.
//

#ifndef RENDERITEM_H
#define RENDERITEM_H

#include "SFML/Graphics/Drawable.hpp"


class RenderItem: public sf::Drawable {
public:
    void draw(sf::RenderTarget &target, sf::RenderStates states) const override = 0;

    virtual void update(float dt) = 0;
};



#endif //RENDERITEM_H
