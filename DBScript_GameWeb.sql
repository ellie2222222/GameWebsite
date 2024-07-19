USE master
go

DROP DATABASE IF EXISTS Game_Website
go

CREATE DATABASE Game_Website
go

USE Game_Website
go

-- Create the 'users' table to store user information
CREATE TABLE users (
    user_id int PRIMARY KEY IDENTITY(1,1),
    username NVARCHAR(50) NOT NULL,
    email VARCHAR(100) NOT NULL UNIQUE,
    password_hash VARCHAR(255) NOT NULL,
	is_admin TINYINT NOT NULL,
	remember_me_token VARCHAR(255) NULL,
	balance DECIMAL(10, 2) DEFAULT 0
);

-- Create the 'games' table to store information about the games
CREATE TABLE games (
    game_id int PRIMARY KEY IDENTITY(1,1),
    title NVARCHAR(100) NOT NULL,
    description TEXT,
    price DECIMAL(10, 2) NOT NULL,
    release_date DATE,
    platform VARCHAR(50),
    publisher VARCHAR(100)
);

CREATE TABLE game_images (
	image_id int PRIMARY KEY IDENTITY(1, 1),
	image_path VARCHAR(255) NOT NULL,
	game_id int,
	FOREIGN KEY (game_id) REFERENCES games(game_id)
)

CREATE TABLE game_genres (
	game_id int,
	genre varchar(50) NOT NULL,
	FOREIGN KEY (game_id) REFERENCES games(game_id)
)

-- Create the 'user_cart' table to track users' shopping carts
CREATE TABLE user_cart (
    cart_id int PRIMARY KEY IDENTITY(1,1),
    user_id int,
    game_id int UNIQUE,
    FOREIGN KEY (user_id) REFERENCES users(user_id),
    FOREIGN KEY (game_id) REFERENCES games(game_id)
);

CREATE TABLE user_library (
    library_id int PRIMARY KEY IDENTITY(1,1),
    user_id int,
    game_id int
    FOREIGN KEY (user_id) REFERENCES users(user_id),
    FOREIGN KEY (game_id) REFERENCES games(game_id)
);

-- Create the 'orders' table to store order information
CREATE TABLE orders (
    order_id int PRIMARY KEY IDENTITY(1,1),
    user_id int,
    order_date DATETIME,
    total_amount DECIMAL(10, 2) NOT NULL,
    FOREIGN KEY (user_id) REFERENCES users(user_id)
);

-- Create the 'order_details' table to store details of each ordered item
CREATE TABLE order_details (
    order_detail_id int PRIMARY KEY IDENTITY(1,1),
    order_id int,
    game_id int,
    price DECIMAL(10, 2) NOT NULL,
    FOREIGN KEY (order_id) REFERENCES orders(order_id),
    FOREIGN KEY (game_id) REFERENCES games(game_id)
);

INSERT INTO games (title, description, price, release_date, platform, publisher)
VALUES 
    ('The Witcher 3: Wild Hunt', 'Open-world RPG', 29.99, '2015-05-19', 'PC', 'CD Projekt'),
	('World of Warcraft', 'MMORPG', 14.99, '2004-11-23', 'PC', 'Blizzard Entertainment'),
    ('Fallout 4', 'Action RPG', 29.99, '2015-11-10', 'PC', 'Bethesda Softworks'),
    ('League of Legends', 'MOBA', 0.00, '2009-10-27', 'PC', 'Riot Games'),
    ('Dota 2', 'MOBA', 0.00, '2013-07-09', 'PC', 'Valve'),
    ('Minecraft', 'Sandbox game', 26.95, '2011-11-18', 'PC', 'Mojang'),
    ('Stardew Valley', 'Farming simulation game', 14.99, '2016-02-26', 'PC', 'ConcernedApe'),
    ('Cities: Skylines', 'City-building game', 29.99, '2015-03-10', 'PC', 'Paradox Interactive'),
    ('Civilization VI', 'Turn-based strategy game', 59.99, '2016-10-21', 'PC', '2K Games'),
    ('The Sims 4', 'Life simulation game', 39.99, '2014-09-02', 'PC', 'Electronic Arts')

INSERT INTO game_images (game_id, image_path) VALUES (1, 'assets/images/thewitcher3_wildhunt_main.jpg')
INSERT INTO game_images (game_id, image_path) VALUES (1, 'assets/images/thewitcher3_wildhunt_header.jpg')
INSERT INTO game_images (game_id, image_path) VALUES (2, 'assets/images/worldofwarcraft_main.jpg')
INSERT INTO game_images (game_id, image_path) VALUES (2, 'assets/images/worldofwarcraft_header.jpg')
INSERT INTO game_images (game_id, image_path) VALUES (3, 'assets/images/fallout4_main.jpg')
INSERT INTO game_images (game_id, image_path) VALUES (3, 'assets/images/fallout4_header.jpg')
INSERT INTO game_images (game_id, image_path) VALUES (4, 'assets/images/lol_main.jpg')
INSERT INTO game_images (game_id, image_path) VALUES (4, 'assets/images/lol_header.jpg')
INSERT INTO game_images (game_id, image_path) VALUES (4, 'assets/images/lol_slideshow.jpg')
INSERT INTO game_images (game_id, image_path) VALUES (5, 'assets/images/dota2_main.jpg')
INSERT INTO game_images (game_id, image_path) VALUES (5, 'assets/images/dota2_header.jpg')
INSERT INTO game_images (game_id, image_path) VALUES (6, 'assets/images/minecraft_main.jpg')
INSERT INTO game_images (game_id, image_path) VALUES (6, 'assets/images/minecraft_header.jpg')
INSERT INTO game_images (game_id, image_path) VALUES (6, 'assets/images/minecraft_slideshow.jpg')
INSERT INTO game_images (game_id, image_path) VALUES (7, 'assets/images/stardewvalley_main.jpg')
INSERT INTO game_images (game_id, image_path) VALUES (7, 'assets/images/stardewvalley_header.jpg')
INSERT INTO game_images (game_id, image_path) VALUES (8, 'assets/images/citiesskyline_main.jpg')
INSERT INTO game_images (game_id, image_path) VALUES (8, 'assets/images/citiesskyline_header.jpg')
INSERT INTO game_images (game_id, image_path) VALUES (9, 'assets/images/civilizationvi_main.jpg')
INSERT INTO game_images (game_id, image_path) VALUES (9, 'assets/images/civilizationvi_header.jpg')
INSERT INTO game_images (game_id, image_path) VALUES (10, 'assets/images/thesims4_main.jpg')
INSERT INTO game_images (game_id, image_path) VALUES (10, 'assets/images/thesims4_header.jpg')

INSERT INTO game_genres (game_id, genre) VALUES(1, 'Adventure')
INSERT INTO game_genres (game_id, genre) VALUES(1, 'RPG')
INSERT INTO game_genres (game_id, genre) VALUES(1, 'Drama')
INSERT INTO game_genres (game_id, genre) VALUES(1, 'Fantasy')
INSERT INTO game_genres (game_id, genre) VALUES(1, 'Open World')
INSERT INTO game_genres (game_id, genre) VALUES(2, 'RPG')
INSERT INTO game_genres (game_id, genre) VALUES(2, 'MMO')
INSERT INTO game_genres (game_id, genre) VALUES(2, 'Fantasy')
INSERT INTO game_genres (game_id, genre) VALUES(3, 'RPG')
INSERT INTO game_genres (game_id, genre) VALUES(3, 'FPS')
INSERT INTO game_genres (game_id, genre) VALUES(3, 'Open World')
INSERT INTO game_genres (game_id, genre) VALUES(4, 'MOBA')
INSERT INTO game_genres (game_id, genre) VALUES(4, 'Strategy')
INSERT INTO game_genres (game_id, genre) VALUES(5, 'MOBA')
INSERT INTO game_genres (game_id, genre) VALUES(5, 'Strategy')
INSERT INTO game_genres (game_id, genre) VALUES(6, 'Sandbox')
INSERT INTO game_genres (game_id, genre) VALUES(6, 'Survival')
INSERT INTO game_genres (game_id, genre) VALUES(6, 'Open World')
INSERT INTO game_genres (game_id, genre) VALUES(7, 'RPG')
INSERT INTO game_genres (game_id, genre) VALUES(7, 'Simulation')
INSERT INTO game_genres (game_id, genre) VALUES(7, 'Open World')
INSERT INTO game_genres (game_id, genre) VALUES(8, 'Tycoon')
INSERT INTO game_genres (game_id, genre) VALUES(8, 'Simulation')
INSERT INTO game_genres (game_id, genre) VALUES(9, 'Turn-Based Strategy')
INSERT INTO game_genres (game_id, genre) VALUES(9, '4X')
INSERT INTO game_genres (game_id, genre) VALUES(10, 'Simulation')

select * from users	
SELECT * FROM games
SELECT * FROM game_images
SELECT * FROM game_genres
SELECT * FROM user_cart
SELECT * FROM user_library
SELECT * FROM orders
SELECT * FROM order_details
-- RESET IDENTITY(1, 1)
DBCC CHECKIDENT ('user_cart', RESEED, 0);
DBCC CHECKIDENT ('user_library', RESEED, 0);
--UPDATE users SET is_admin = 1 WHERE username = 'admin' 

UPDATE games SET description = 'You are Geralt of Rivia, mercenary monster slayer. Before you stands a war-torn, monster-infested continent you can explore at will. Your current contract? Tracking down Ciri - the Child of Prophecy, a living weapon that can alter the shape of the world.
\n
\nUpdated to the latest version, The Witcher 3: Wild Hunt comes with new features and items, including a built-in Photo Mode, swords, armor, and alternate outfits inspired by The Witcher Netflix series - and more!
\n
\nBehold the dark fantasy world of the Continent like never before! This edition of The Witcher 3: Wild Hunt has been enhanced with numerous visual and technical improvements, including vastly improved level of detail, a range of community created and newly developed mods for the game, real-time ray tracing, and more - all implemented with the power of modern PCs in mind.
\n
\nTrained from early childhood and mutated to gain superhuman skills, strength, and reflexes, witchers are a counterbalance to the monster-infested world in which they live.
\n
\n• Gruesomely destroy foes as a professional monster hunter armed with a range of upgradeable weapons, mutating potions, and combat magic.
\n• Hunt down a wide variety of exotic monsters, from savage beasts prowling mountain passes to cunning supernatural predators lurking in the shadowy back alleys of densely populated cities.
\n• Invest your rewards to upgrade your weaponry and buy custom armor, or spend them on horse races, card games, fist fighting, and other pleasures life brings.
\n
\nBuilt for endless adventure, the massive open world of The Witcher sets new standards in terms of size, depth, and complexity.
\n• Traverse a fantastical open world: explore forgotten ruins, caves, and shipwrecks, trade with merchants and dwarven smiths in cities, and hunt across the open plains, mountains, and seas.
\n• Deal with treasonous generals, devious witches, and corrupt royalty to provide dark and dangerous services.
\n• Make choices that go beyond good & evil, and face their far-reaching consequences.
\n
\nTake on the most important contract of your life: to track down the child of prophecy, the key to saving or destroying this world.
\n• In times of war, chase down the child of prophecy, a living weapon foretold by ancient elven legends.
\n• Struggle against ferocious rulers, spirits of the wilds, and even a threat from beyond the veil – all hell-bent on controlling this world.
\n• Define your destiny in a world that may not be worth saving.'
WHERE title = 'The Witcher 3: Wild Hunt'

UPDATE games SET description = 'Embark on a grand journey in the mystical world of Azeroth, a land teetering on the brink of chaos. Immerse yourself in the latest version of World of Warcraft, where epic adventures await at every turn, and new features enhance your gaming experience.
\n
\nStep into the shoes of a hero, chosen by destiny, as you navigate through a sprawling, war-torn realm besieged by ancient evils and rival factions. Your quest? To forge alliances, conquer dungeons, and face off against the formidable forces threatening the very fabric of Azeroth.
\n
\nDive into the updated world with cutting-edge additions, including dynamic new battlegrounds, powerful artifacts, and customizable mounts. Revel in the beauty of the game''s breathtaking landscapes, brought to life with improved graphics, lighting effects, and a revamped user interface.
\n
\nChoose your path and class wisely, as you join the ranks of warriors, mages, rogues, and more. Develop your character''s skills, acquire legendary gear, and engage in epic battles against monstrous foes and rival players.
\n
\nCraft your own narrative as you traverse diverse environments – from lush forests and sprawling deserts to frozen tundras and towering mountain ranges. Interact with a multitude of races, forge alliances, or succumb to the temptations of the dark side, each decision shaping your destiny.
\n
\nParticipate in dynamic player-driven events, guild wars, and massive raids that will test your strategic prowess and combat skills. Enhance your character with unique abilities, talents, and an array of magical spells to become a legendary champion in the ever-evolving world of Warcraft.
\n
\nEngage in the rich lore of Azeroth, encountering legendary figures, ancient mysteries, and formidable adversaries. Unravel the threads of a complex narrative, where choices echo across the continents, affecting the destiny of not only your character but the entire world.
\n
\nJoin forces with friends in cooperative gameplay or challenge rivals in intense player-versus-player combat. The world of Warcraft is alive with endless possibilities, providing an unparalleled gaming experience that transcends the boundaries of traditional MMORPGs.
\n
\nPrepare for an odyssey of epic proportions – the fate of Azeroth rests in your hands. Will you rise to become a legendary hero or succumb to the darkness that threatens to engulf the world?'
WHERE title = 'World of Warcraft'

UPDATE games SET description = 'Step into the post-apocalyptic wasteland of Fallout 4, a gripping and immersive role-playing adventure set in a world ravaged by nuclear war. In this latest iteration, witness a meticulously crafted open-world experience that seamlessly blends a gripping narrative with unparalleled freedom.
\n
\nAssume the role of the Sole Survivor, a character who emerges from an underground vault to discover a desolate and irradiated landscape. Unravel the mystery of your own past as you navigate through the ruins of a once-thriving Boston, now transformed into a sprawling, war-torn wasteland filled with danger and opportunity.
\n
\nExplore the vast, dynamic Commonwealth – a region teeming with mutated creatures, factions vying for power, and hidden treasures waiting to be uncovered. The game''s updated version introduces enhanced visuals, including improved lighting effects, weather dynamics, and a host of new weapons and gear to adapt to the ever-evolving challenges of the wasteland.
\n
\nShape your character''s destiny with a robust and flexible leveling system. Develop skills, acquire perks, and customize your playstyle with an extensive array of weapons, armor, and advanced technological gadgets. Build and upgrade settlements, recruiting survivors to create thriving communities or fortifying structures against the constant threat of raiders and mutant creatures.
\n
\nEncounter a diverse cast of characters with their own stories, motives, and secrets. Forge alliances or make enemies as your choices ripple through the Commonwealth, influencing the fate of its inhabitants. Engage in intense moral dilemmas, where decisions carry weight and consequences are far-reaching.
\n
\nImmerse yourself in the retro-futuristic aesthetic of the Fallout universe, featuring iconic music, architecture, and the ever-present Vault-Tec propaganda. Uncover the lore of the series through hidden terminals, holotapes, and the remnants of a bygone era, piecing together the history that led to the world''s devastation.
\n
\nSurvive the harsh realities of the wasteland by scavenging for resources, crafting essential items, and mastering the art of combat. Face mutated monstrosities, rival factions, and the ever-looming threat of radioactive storms as you traverse the remains of a once-thriving civilization.
\n
\nEmbark on a personal quest for answers, pursuing the trail of your missing son while navigating the complex web of political intrigue and moral ambiguity. The Commonwealth is yours to shape, and the choices you make will define not only your own fate but the future of this irradiated world.'
WHERE title = 'Fallout 4';

UPDATE games SET description = 'Dive into the fast-paced, strategic world of League of Legends, a multiplayer online battle arena (MOBA) that combines intense team-based competition with powerful champions and ever-evolving gameplay. Experience the thrill of the Summoner''s Rift with new champions, dynamic game modes, and enhanced features.
\n
\nBecome a summoner and assemble a team of diverse champions, each with unique abilities and playstyles. Choose from a constantly expanding roster of champions, each meticulously designed with their own backstories and visual flair. Engage in strategic battles, where teamwork, skill, and adaptability are crucial to victory.
\n
\nExplore the visually stunning world of Runeterra, with its diverse landscapes and iconic regions. The updated version introduces stunning graphics, improved animations, and immersive sound design, creating an unparalleled gaming experience that brings the champions and their abilities to life.
\n
\nEngage in thrilling 5v5 battles on the iconic Summoner''s Rift, working with your team to destroy the enemy Nexus while fending off opposing champions. Immerse yourself in the ever-evolving metagame, where new patches introduce balance changes, champion reworks, and innovative items, keeping the gameplay fresh and exciting.
\n
\nDiscover a variety of game modes catering to different playstyles, from the intense competition of ranked matches to the casual fun of rotating game modes. Participate in special events, seasonal updates, and global tournaments that showcase the skill and dedication of the League of Legends community.
\n
\nCustomize your champions with an extensive array of skins, emotes, and other cosmetic items. Showcase your personal style and accomplishments on the battlefield, standing out among millions of players worldwide. The updated version introduces new visual effects, animations, and voice lines for your favorite champions, elevating the overall aesthetic of the game.
\n
\nMaster the art of teamwork and strategy as you climb the ranks in the competitive ladder. Communicate with your teammates, coordinate epic plays, and outsmart your opponents to secure victory. With a commitment to fair play and regular updates, League of Legends remains a dynamic and engaging experience for both newcomers and seasoned veterans.
\n
\nJoin a global community of players, content creators, and esports enthusiasts, where the passion for League of Legends extends beyond the game itself. Whether you''re a solo player or part of a dedicated team, the world of Runeterra awaits, offering endless possibilities for triumph, glory, and unforgettable moments.'
WHERE title = 'League of Legends';

UPDATE games SET description = 'Immerse yourself in the world of Dota 2, a multiplayer online battle arena (MOBA) that delivers intense team-based competition, strategic gameplay, and a roster of diverse heroes. In this latest update, embark on a journey through the mystical realms of Dota, encountering new heroes, dynamic game modes, and enhanced features that redefine the battlefield.
\n
\nAssemble your team and choose from a vast array of heroes, each equipped with unique abilities and distinct playstyles. Dive into strategic battles, where teamwork, skill, and adaptability are the keys to victory in this ever-evolving game.
\n
\nExplore the rich landscapes of the Dota universe, enhanced with updated graphics, improved animations, and immersive soundscapes that breathe life into each hero and their abilities. Engage in epic 5v5 battles on iconic battlegrounds, where your team strives to demolish the enemy Ancient while facing off against formidable foes.
\n
\nExperience the dynamic gameplay influenced by regular patches introducing hero balances, new items, and innovative mechanics, ensuring a continually fresh and exciting experience. From competitive ranked matches to casual game modes, Dota 2 caters to diverse playstyles and offers a rich tapestry of strategic possibilities.
\n
\nCustomize your favorite heroes with an extensive selection of cosmetics, including skins, voice lines, and other items. Showcase your unique style and accomplishments on the battlefield, standing out among millions of players worldwide. The updated version introduces new visual effects, animations, and voice lines, elevating the aesthetic appeal of your chosen heroes.
\n
\nClimb the ranks in the competitive ladder, mastering the intricacies of teamwork and strategy. Communicate with your teammates, execute coordinated plays, and outwit your adversaries to secure victory. Dota 2, committed to fairness and regular updates, provides an immersive and dynamic experience for both newcomers and seasoned veterans.
\n
\nJoin a global community of players, content creators, and esports enthusiasts, where the passion for Dota 2 extends far beyond the game itself. Whether you''re a lone warrior or part of a dedicated team, the Dota universe awaits, promising endless opportunities for triumph, glory, and unforgettable moments.'
WHERE title = 'Dota 2';

UPDATE games SET description = 'Embark on a limitless adventure in the iconic world of Minecraft, a sandbox game that grants you the power to shape your own reality. In this latest update, delve into a realm of infinite creativity, new features, and enhanced gameplay that redefine the blocky landscapes you explore.
\n
\nBecome the architect of your dreams as you gather resources, build structures, and face the challenges of the blocky universe. Minecraft offers a truly open-ended experience, where your imagination sets the limits. Craft tools, mine valuable materials, and construct anything from humble cottages to sprawling cities.
\n
\nDiscover the pixelated beauty of procedurally generated worlds, teeming with diverse biomes, creatures, and hidden wonders. The updated version introduces improved graphics, lighting effects, and immersive soundscapes, breathing life into the charming, voxel-based environment.
\n
\nEmbark on a solo adventure or collaborate with friends in multiplayer mode, where your creativity knows no bounds. Encounter hostile mobs, explore vast caverns, and uncover the mysteries hidden beneath the surface as you strive to survive and thrive.
\n
\nExperience the thrill of countless gameplay modes, from Survival mode''s intense challenges to Creative mode''s boundless possibilities. Engage in exciting community-created mods and maps, adding layers of depth and variety to your Minecraft experience.
\n
\nCustomize your world with an array of skins, textures, and add-ons that reflect your unique style. Whether you prefer medieval castles, futuristic cities, or whimsical landscapes, Minecraft provides the canvas for your artistic expression.
\n
\nMaster the art of Redstone and command blocks to create intricate contraptions, automated systems, and interactive experiences. The constantly evolving nature of Minecraft ensures that each update brings new surprises, features, and adventures to the table.
\n
\nJoin a global community of builders, explorers, and storytellers, where the love for Minecraft extends beyond the virtual realms. Whether you''re a solo player or part of a bustling server, the blocky landscapes of Minecraft invite you to shape your own narrative, forge friendships, and create memories that will last a lifetime.'
WHERE title = 'Minecraft';

UPDATE games SET description = 'Embark on a heartwarming journey to the rural haven of Stardew Valley, a farming simulation game that invites you to escape the hustle and bustle of city life. In this latest update, immerse yourself in the idyllic charm of Pelican Town, with new features, characters, and enhanced gameplay that make your virtual farm life even more enchanting.
\n
\nInherit a run-down farm and transform it into a thriving homestead. Plant crops, raise animals, and build relationships with the friendly inhabitants of Pelican Town. Stardew Valley offers a tranquil escape where the seasons change, and your decisions shape the world around you.
\n
\nMeet a diverse cast of characters, each with their own stories and quirks. Engage in friendships, romance, and community events as you become an integral part of this close-knit society. The updated version introduces new events, festivals, and heartwarming interactions, deepening the bonds you form with the residents of Pelican Town.
\n
\nExplore the picturesque landscapes of Stardew Valley, filled with charming pixel art and soothing soundscapes. The updated version enhances the visual and auditory experience, bringing the cozy farm life to vibrant, pixelated life.
\n
\nEmbark on various activities, from fishing in the tranquil rivers to mining for precious resources in the mysterious caves. With the addition of new crops, animals, and farm upgrades, the possibilities for your flourishing farm are endless.
\n
\nUncover the secrets of the Valley, participate in seasonal festivals, and discover the magic that lies within the nearby caves. Stardew Valley celebrates the simple joys of life, offering a delightful escape into a world where hard work, community, and a bit of magic make every day special.
\n
\nCustomize your farm, house, and character with a plethora of options, showcasing your unique style. The updated version introduces additional customization features, allowing you to create a farm that reflects your personality and creativity.
\n
\nJoin a vibrant community of players, share tips and stories, and experience the joy of virtual farming together. Stardew Valley remains a heartwarming, timeless experience that invites you to cultivate not just crops but also lasting memories.'
WHERE title = 'Stardew Valley';

UPDATE games SET description = 'Shape the skyline of your dream city in Cities: Skylines, a city-building simulation game that puts you in control of every aspect of urban planning. In this latest update, embrace the challenges of urban development with new features, realistic city dynamics, and enhanced gameplay that elevate your city-building experience.
\n
\nTake on the role of a mayor and design a metropolis from the ground up. Plan intricate road networks, strategically zone residential, commercial, and industrial areas, and provide essential services to meet the needs of your growing population. Cities: Skylines offers unparalleled freedom to create and manage a city that reflects your vision and ingenuity.
\n
\nDelve into the intricacies of city management with realistic simulations of traffic, public services, and citizen behavior. The updated version introduces advanced city dynamics, improved AI, and additional urban challenges, offering a more immersive and rewarding experience for both novice and seasoned city builders.
\n
\nWitness the visual splendor of your city as it comes to life with vibrant graphics, realistic day-night cycles, and dynamic weather effects. The updated version enhances the aesthetic appeal of your city, creating a visually stunning and dynamic urban landscape.
\n
\nTackle the complexities of urban life, from managing traffic flow and public transportation to addressing environmental concerns and disasters. With new scenarios, policies, and city services, Cities: Skylines offers a deep and engaging simulation that challenges your strategic planning skills.
\n
\nCustomize your city with a variety of mods and assets created by a dedicated community of players. From unique buildings to gameplay enhancements, the modding community adds an extra layer of creativity and diversity to your city-building experience.
\n
\nParticipate in city challenges, unlock unique landmarks, and optimize your city''s efficiency to reach new milestones. Whether you aim for a bustling metropolis or a serene utopia, Cities: Skylines provides the canvas for your urban masterpiece.
\n
\nJoin a global community of city builders, share your creations, and explore the impressive cities crafted by others. Cities: Skylines continues to be a benchmark in city-building simulation, offering endless possibilities for urban planning, creativity, and strategic decision-making.'
WHERE title = 'Cities: Skylines';