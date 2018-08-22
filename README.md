# Football-Club-Rest-Api

## Tech:
* Maven
* Spring Boot 
* Spring MVC 
* Spring Data 
* Spring HATEOAS 
* Swagger

### Football club:
* download a list of football clubs -> **GET** /fbclubs
* add a football club -> **POST** /fbclubs
* delete a football club -> **DELETE** /fbclubs/id
* update a football club -> **PUT** /fbclubs/id
* download details of the selected football club -> **GET** /fbclubs/id


### Football club->players
* download all players for a football club -> **GET** /fbclubs/id/players
* add a player for a football club -> **POST** /fbclubs/id/players
* download details of the selected player -> **GET** /fbclubs/id/players/player_id

#### Example data(Football club) in json format: 
```
{
        "name": "Club name",
        "league": "Premier League",
        "coach": "Jan Kowalski"
}
```

#### Example data(Player) in json format: 
```
{
         "name": "Smith Smith",
         "shirtNumber": 10,
         "position": "Winger"
}
```
