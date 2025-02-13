#include <iostream>

#include "App.h"
#include "items/SimpleTextItem.h"
#include "items/TypewriterText.h"

std::shared_ptr<SimpleTextItem> getSimpleTextItem(const sf::Font &font, const std::string &text,
                                                  const unsigned int characterSize, const sf::Color &color,
                                                  const sf::Vector2f &position, const bool centerOrigin) {
    sf::Text sfText{font, text, characterSize};
    sfText.setFillColor(color);
    if (centerOrigin) {
        const sf::FloatRect bounds = sfText.getLocalBounds();
        sfText.setOrigin({bounds.size.x / 2, bounds.size.y / 2});
    }
    sfText.setPosition(position);
    return std::make_shared<SimpleTextItem>(sfText);
}

std::shared_ptr<SimpleTextItem> getSimpleTextItem(const sf::Font &font, const std::string &text,
                                                  const unsigned int characterSize, const sf::Color &color,
                                                  const sf::Vector2f &position, const bool centerOrigin,
                                                  const sf::Vector2f &positionChange,
                                                  const float duration) {
    sf::Text sfText{font, text, characterSize};
    sfText.setFillColor(color);
    if (centerOrigin) {
        const sf::FloatRect bounds = sfText.getGlobalBounds();
        sfText.setOrigin({bounds.size.x / 2, bounds.size.y / 2});
    }
    sfText.setPosition(position);
    SimpleTextItem item{sfText};
    item.setPositionChange(positionChange, duration);
    return std::make_shared<SimpleTextItem>(item);
}

std::shared_ptr<TypewriterText> getTypewriterText(const sf::Font &font, const std::string &text,
                                                  const unsigned int characterSize, const sf::Color &color,
                                                  const sf::Vector2f &position, const bool centerOrigin,
                                                  const float duration) {
    sf::Text sfText{font, text, characterSize};
    sfText.setFillColor(color);
    if (centerOrigin) {
        const sf::FloatRect bounds = sfText.getGlobalBounds();
        sfText.setOrigin({bounds.size.x / 2, bounds.size.y / 2});
    }
    sfText.setPosition(position);
    return std::make_shared<TypewriterText>(sfText, duration);
}

// Erm doesn't work
std::shared_ptr<sf::Sprite> getBackgroundImage(const std::string &path) {
    const auto texture = std::make_shared<sf::Texture>();
    if (!texture->loadFromFile(path)) {
        std::cerr << "Failed to load image" << std::endl;
        return nullptr;
    }

    auto sprite = std::make_shared<sf::Sprite>(*texture);
    return sprite;
}

int main() {
    App app;

    const sf::Font font{"../../resources/JetBrainsMono-Regular.ttf"};

    constexpr sf::Vector2f center = {960, 540};

    std::map<unsigned int, std::vector<std::shared_ptr<RenderItem> > > frameItems;

    frameItems[0].push_back(getSimpleTextItem(font, "Intro", 120, sf::Color::Yellow, center, true));
    KeyFrame keyFrame1{frameItems};
    app.addKeyFrame(std::make_shared<KeyFrame>(keyFrame1));
    frameItems.clear();

    frameItems[0].push_back(getSimpleTextItem(font, "EBNF", 90, sf::Color::White, center, true));
    KeyFrame keyFrame2{frameItems};
    app.addKeyFrame(std::make_shared<KeyFrame>(keyFrame2));
    frameItems.clear();

    std::string text = "digit = '0' | '1' | '2' | '3' | '4' | '5' | '6' | '7' | '8' | '9'.\n"
                       "sign = '+' | '-'.\n"
                       "integer = [sign] digit {digit}.";
    frameItems[0].push_back(getSimpleTextItem(font, text, 40, sf::Color::White, center, true));
    KeyFrame keyFrame3{frameItems};
    app.addKeyFrame(std::make_shared<KeyFrame>(keyFrame3));
    frameItems.clear();

    text = "program = 'main' '(' ')' '{' instruction_list '}'.\n"
           "instruction_list = instruction | instruction instruction_list.\n"
           "instruction = declaration ';' | assignment ';' | control | loop | input ';' | output ';'.\n\n"
           "declaration = type ID | type ID '=' expression.\n"
           "assignment = ID '=' expression.\n"
           "type = 'int' | 'float'.\n"
           "expression = ID | CONST | expression arithmetic_op expression | '(' expression ')'.\n"
           "arithmetic_op = '+' | '-' | '*' | '/' | '%'.\n"
           "input = 'cin' '>>' ID.\n"
           "output = 'cout' '<<' expression.\n\n"
           "control = 'if' '(' logical_expression ')' '{' instruction_list '}'.\n"
           "control = 'if' '(' logical_expression ')' '{' instruction_list '}' 'else' '{' instruction_list '}'.\n"
           "logical_expression = expression comparison_op expression.\n"
           "logical_expression = logical_expression logical_op logical_expression.\n"
           "comparison_op = '==' | '!=' | '<' | '<=' | '>' | '>='.\n"
           "logical_op = '&&' | '||'.\n"
           "loop = 'while' '(' logical_expression ')' '{' instruction_list '}'.";

    frameItems[0].push_back(getTypewriterText(font, text, 30, sf::Color::White, center, true, 20));
    KeyFrame keyFrame4{frameItems};
    app.addKeyFrame(std::make_shared<KeyFrame>(keyFrame4));
    frameItems.clear();

    frameItems[0].push_back(getSimpleTextItem(font, "Formal languages", 60, sf::Color::White, {960, 150}, true));
    frameItems[1].push_back(getSimpleTextItem(font, "L = {a^n c | n > 0}", 50, sf::Color::White, {960, 250}, true));
    frameItems[2].push_back(getSimpleTextItem(font, "ac\naac\naaac", 40, sf::Color::Green, {960, 360}, true));
    frameItems[3].push_back(getSimpleTextItem(font, "b\nabc", 40, sf::Color::Red, {960, 520}, true));

    KeyFrame keyFrame5{frameItems};
    app.addKeyFrame(std::make_shared<KeyFrame>(keyFrame5));
    frameItems.clear();

    frameItems[0].push_back(getSimpleTextItem(font, "Stages of Compiling", 70, sf::Color::White, {960, 100}, true));
    frameItems[1].push_back(getSimpleTextItem(font, "Lexical Analysis", 50, sf::Color::White, {960, 300}, true));
    frameItems[2].push_back(getSimpleTextItem(font, "Syntax Analysis", 50, sf::Color::White, {960, 400}, true));
    frameItems[3].push_back(getSimpleTextItem(font, "Semantic Analysis", 50, sf::Color::White, {960, 500}, true));
    frameItems[4].push_back(getSimpleTextItem(font, "Intermediate Code Generation", 50, sf::Color::White, {960, 600}, true));
    frameItems[5].push_back(getSimpleTextItem(font, "Code Optimization", 50, sf::Color::White, {960, 700}, true));
    frameItems[6].push_back(getSimpleTextItem(font, "Code Generation", 50, sf::Color::White, {960, 800}, true));

    KeyFrame keyFrame6{frameItems};
    app.addKeyFrame(std::make_shared<KeyFrame>(keyFrame6));
    frameItems.clear();

    frameItems[0].push_back(getSimpleTextItem(font, "Finite State Machine", 100, sf::Color::White, {960, 400}, true));
    frameItems[0].push_back(getSimpleTextItem(font, "or", 100, sf::Color::White, {960, 500}, true));
    frameItems[0].push_back(getSimpleTextItem(font, "Finite Automaton", 100, sf::Color::White, {960, 630}, true));
    KeyFrame keyFrame7{frameItems};
    app.addKeyFrame(std::make_shared<KeyFrame>(keyFrame7));
    frameItems.clear();

    frameItems[0].push_back(getSimpleTextItem(font, "Grammar", 70, sf::Color::White, {960, 300}, true));
    frameItems[1].push_back(getTypewriterText(font, "EBNF: declaration = type ID | type ID '=' expression.", 50, sf::Color::White, {960, 500}, true, 4));
    frameItems[2].push_back(getTypewriterText(font, "Grammar: _declaration -> _type ID | _type ID = _expression", 50, sf::Color::White, {960, 600}, true, 4));
    KeyFrame keyFrame8{frameItems};
    app.addKeyFrame(std::make_shared<KeyFrame>(keyFrame8));
    frameItems.clear();

    frameItems[0].push_back(getSimpleTextItem(font, "Parsing", 70, sf::Color::White, {960, 300}, true));
    frameItems[1].push_back(getSimpleTextItem(font, "LL 1 Parsing", 50, sf::Color::White, {960, 450}, true));
    frameItems[2].push_back(getSimpleTextItem(font, "First", 50, sf::Color::White, {960, 550}, true));
    frameItems[3].push_back(getSimpleTextItem(font, "Follow", 50, sf::Color::White, {960, 650}, true));
    KeyFrame keyFrame9{frameItems};
    app.addKeyFrame(std::make_shared<KeyFrame>(keyFrame9));
    frameItems.clear();

    frameItems[0].push_back(getSimpleTextItem(font, "Other options?", 70, sf::Color::White, {960, 300}, true));
    frameItems[1].push_back(getSimpleTextItem(font, "Tools like Lex/Flex and Yacc/Bison", 50, sf::Color::White, {960, 450}, true));
    frameItems[2].push_back(getSimpleTextItem(font, "Why?", 50, sf::Color::White, {960, 550}, true));
    frameItems[3].push_back(getSimpleTextItem(font, "Rest of video using these", 40, sf::Color::White, {960, 750}, true));
    KeyFrame keyFrame10{frameItems};
    app.addKeyFrame(std::make_shared<KeyFrame>(keyFrame10));
    frameItems.clear();

    frameItems[0].push_back(getSimpleTextItem(font, "What's next?", 70, sf::Color::White, {960, 300}, true));
    frameItems[1].push_back(getSimpleTextItem(font, "Expand Lex/Flex Yacc/Bison", 50, sf::Color::White, {960, 450}, true));
    frameItems[2].push_back(getSimpleTextItem(font, "Adding attributed grammar to the custom compiler", 50, sf::Color::White, {960, 550}, true));
    frameItems[3].push_back(getSimpleTextItem(font, "Intermediate Code Generation and Code Generation", 50, sf::Color::White, {960, 650}, true));
    KeyFrame keyFrame11{frameItems};
    app.addKeyFrame(std::make_shared<KeyFrame>(keyFrame11));
    frameItems.clear();

    frameItems[0].push_back(getTypewriterText(font, "Thank you for watching!", 70, sf::Color::Cyan, center, true, 5));
    KeyFrame keyFrame12{frameItems};
    app.addKeyFrame(std::make_shared<KeyFrame>(keyFrame12));
    frameItems.clear();

    app.run();
    return 0;
}
