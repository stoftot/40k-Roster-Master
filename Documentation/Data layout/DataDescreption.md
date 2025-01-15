## GENERAL NOTES
Add a filter to filter things from non officialy palyabel sources, such as legeneds


## Factions
Has simpel information about the faction

id: the string identifyioen the faction
name: name of the faction
link: link to the faction on wahapedia


## Source
##### This tabel is irelevant for the application
Has the information on where the different rules and other stuff on wahapedia is from.

only the following fields, are relevant
id, type


id: the id of the source
name: the name of the rules
type: the type of the rules e.g. index, codex expansion.
as far as I can see everything excepr index and codex, are not playabel in officaly turnaments,  
edition: the edetion of the rules
version: the version of the rules
errata_date: the date when it was added to wahapedia
errata_link: the link to where its saved


## Datasheets
Has general information about the datasheets, and how they are related to other tabels

id: the id for the datasheet
name: name of the datasheet
faction_id: the id of the faction of the datasheet conected to the faction tabel
source_id: the source id of the datasheet conected to the source tabel
legend: fluff text for the datasheet
role: what role the datasheet is, battle line, character, etc.
loadout: the standart loadout of the datasheet, its written in html format
![alt text](image.png)
transport: the transport capcity if its a transport, written as html format, with styling from the website
![alt text](image-1.png)
virtual: a bool for somtehing, not quite sure what, but its only 3 datasheets that have it, but cant find those datasheets on the website. Should proabelry not load the datasheets that has it as true.
leader_head: Leader section header commentary, cant find any datasheets that have it, so proabelry noit relevant.
leader_footer: Leader section footer commentary, if the unit is a leader, its written in html format with styilng from the website.
![alt text](image-2.png)
damaged_w: for units that has a speciel effect when damaged
damaged_description: what effect the unit has when damaged
![alt text](image-3.png)
link: link to the datasheet on wahapedia


## Datasheets_abilities
Has the informaiton on which abilties a datasheet has and in which order they are displayed

datasheet_id: the id of the datasheet, refferencing the datasheet tabel 
line: the order the abilities is displayed in starting with 1. faction adn core are writtne on the same line.
![alt text](image-4.png)
ability_id: the id of the ability if its a non datasheet ability, e.g. faction. if this is filed use the data from the abillityes tabel 
model: Belonging of this ability to a specific model of the datasheet. This dosent seem relevant since there isent any one that has it.
name: name of the ability
description: the descreption of the ability
type: the type of the ability, core, faction, datasheet 
parameter: the paramter of the ability if its applicable
![alt text](image-5.png)


## Datasheets_keywords.csv
Has the information on what keywords wich models on a datasheet has, both regular and faction.

datasheet_id: the id of the datasheet, connected to the datasheet tabel
keyword: the keyword
model: what models on the datasheet a keyword is applicable for. 
![alt text](image-6.png)
is_faction_keyword: a bool if its a faction keyword or not.


## Datasheets_models.csv
Has the information on the stats of the models in a datasheet, most of the time there is only one type of model but ther can acure multipel.

datasheet_id: the id of the datasheet, connected to the datasheet tabel
line: at what line the models info is displayed at, starting at 1
![alt text](image-7.png)
name: the name of the model
M: the movment characteristic of the model
T: the thoughness characteristic of the modle
Sv: the saving through characteristic of the modle
inv_sv: if applicable, the invul saving through characteristic of the modle
inv_sv_descr if applicable the descreption for the invul saving through e.g. "You cannot re-roll invulnerable saving throws made for this model."
![alt text](image-8.png)
W: the wound characteristic of the modle
Ld: the leadership characteristic of the modle
OC: the objective control characteristic of the modle
base_size: the base size of the physical model
base_size_descr: the descreption of the base size 


## Datasheets_options.csv
This describes the avilable, wargear for a datasheet

datasheet_id
line
button
description


## Datasheets_wargear.csv
datasheet_id
line
line_in_wargear
dice
name
description
range
type
A
BS_WS
S
AP
D


## Datasheets_unit_composition.csv
datasheet_id
line
description


## Datasheets_models_cost.csv
datasheet_id
line
description
cost


## Datasheets_stratagems.csv
datasheet_id
stratagem_id


## Datasheets_enhancements.csv
datasheet_id
enhancement_id


## Datasheets_detachment_abilities.csv
datasheet_id
detachment_ability_id

## Datasheets_leader.csv
datasheet_id
attached_datasheet_id


## Stratagems.csv
id
faction_id
name
type
cp_cost
legend
turn
phase
description
detachment


## Abilities.csv
id
name
legend
faction_id
description


## Enhancements.csv
id
faction_id
name
legend
description
cost
detachment


## Detachment_abilities.csv
id
faction_id
name
legend
description
detachment


## Last_update.csv
last_update
