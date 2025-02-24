Prerequisites
Java Development Kit (JDK) 21
Gradle 8.5 or higher
An OpenWeatherMap API key
Setup
Clone the repository:  
git clone git@github.com:username/repository.git
cd repository
Configure the API key:  Open the WeatherApp.java file and replace the API_KEY value with your OpenWeatherMap API key.  
private static final String API_KEY = "your_api_key_here";
Build and Run
Build the project:  
./gradlew build
To run using city name: 
./gradlew run --args="London"
To run using multiple city names: 
./gradlew run --args="London New York Chicago"
Replace "London" with the desired location.
To run using zipcode, use following command:
./gradlew run --args="94404 94541"

