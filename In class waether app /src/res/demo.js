/*function getWeather() {
    const apiKey = 'a31e7a106635faa6152c41a052f1ab1b';
    const cityInput = "Huddersfield";

    const apiUrl = `https://api.openweathermap.org/data/2.5/weather?q=${cityInput}&appid=${apiKey}`;

    fetch(apiUrl)
        .then(response => response.json())
        .then(data => {
            const weatherInfo = document.getElementById('weatherInfo');
            const temperature = Math.round(data.main.temp - 273.15); // Convert from Kelvin to Celsius
            const weatherIcon = `https://openweathermap.org/img/w/${data.weather[0].icon}.png`;

            weatherInfo.innerHTML = `
                <h2>${data.name}, ${data.sys.country}</h2>
                <img id="weatherIcon" src="${weatherIcon}" alt="Weather Icon">
                <p>Temperature: ${temperature}°C</p>
                <p>Weather: ${data.weather[0].description}</p>
                <p>Pressure: ${data.main.pressure}</p>  
                `;
        })
        .catch(error => {
            console.error('Error fetching weather data:', error);
        });
}*/
function getWeather() {
    fetch('http://localhost:8080/weather')
        .then(response => response.json())
        .then(data => {
            let weatherDataDiv = document.getElementById('weatherInfo');
            data.forEach(entry => {
                let city = entry.city;
                let temperature = entry.temperature;
                let pressure = entry.pressure;
                weatherDataDiv.innerHTML += `<p>City: ${city}<br><br> Temperature: ${temperature}<br><br> Pressure: ${pressure}</p>`;
            })
        })
        .catch(error => console.error('Error:', error));
}
