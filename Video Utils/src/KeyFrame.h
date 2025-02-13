//
// Created by Daniel on 11-Feb-25.
//

#ifndef KEYFRAME_H
#define KEYFRAME_H
#include <map>
#include <memory>
#include <vector>

#include "items/RenderItem.h"
#include "SFML/Graphics/Sprite.hpp"


class KeyFrame {
    unsigned int currentFrame = 0;
    unsigned int totalFrames = 0;

    std::optional<std::shared_ptr<sf::Sprite>> backgroundImage;

    std::map<unsigned int, std::vector<std::shared_ptr<RenderItem>>> frameItems;
public:
    explicit KeyFrame(const std::map<unsigned int, std::vector<std::shared_ptr<RenderItem>>> &frame_items);

    void setBackgroundImage(std::shared_ptr<sf::Sprite> image);

    void draw(sf::RenderTarget &target, const sf::RenderStates &states = sf::RenderStates::Default) const;
    void update(float dt);
    bool goToNextFrame();
};



#endif //KEYFRAME_H
