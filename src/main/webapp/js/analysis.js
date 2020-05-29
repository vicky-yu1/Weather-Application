// Check if user is logged in upon page load

if (sessionStorage.getItem("userLoggedIn").match("false")) {
	console.log("In failed login branch");
  document.location = "index.html"
}

//execute this code immediatley when the page loads to get the sessionData from the backend
//and update the frontend accordingly. (Temp Unit Pref)
$.ajax({
  method: "POST",
  url: "/AnalysisServlet",
  data: {
    method: "initialize",
    username:sessionStorage.getItem("loggedInUsername")
  }
})
  .done(function (response) {
    console.log(response);

    let data = response.split("@");

    //temp unit checks
    if (data[0] == "F") {
      $("#checkbox").prop("checked", true);
      $(".tempUnit").html("F");
    }
    else {
      $("#checkbox").prop("checked", false);
      $(".tempUnit").html("C");
    }

    //display favorite cities list
    if (data[1] != "None") {
      var newCityList = "";

      $("#favoriteCityList").html("");

      var cities = data[1].split("&");
      for (var citydata of cities) {
        var items = citydata.split("_");

        var $newItem = $("<div class='favoriteCityListItem'></div>");

        $newItem.append(`<p class = "name">${items[0]}</p>`);
        $newItem.append(`<span class = "lat" style = "display: none">${items[1]}</span>`);
        $newItem.append(`<span class = "long" style = "display: none">${items[2]}</span></div>`);

        $("#favoriteCityList").append($newItem);


      }
    }
  })
  .fail(function () {
    console.log("error in AJAX request");
  });


//getcurrent date for weather widget
var today = new Date();
var dd = String(today.getDate()).padStart(2, '0');
var mm = String(today.getMonth() + 1).padStart(2, '0'); //January is 0!
var yyyy = today.getFullYear();
today = mm + '/' + dd + '/' + yyyy;
$("#date").text(today);


// Handle click on a city in the favorites list
$("#favoriteCityList").on("click", ".favoriteCityListItem", function () {
  //unmark every as active
  $(".favoriteCityListItem").removeClass("active");
  //mark the clicked city as active
  $(this).addClass("active");
  $(this).fadeOut(50).fadeIn(50);
  //remove the modal to delete
  $("#deleteModal").hide();
  //make a call to the backend to get the data
  var name = $(this).find(".name").text();
  var lat = $(this).find(".lat").text();
  var long = $(this).find(".long").text();
  console.log("getting analysis for "+name+"  "+lat+long);
  getAnalysis(name, lat, long);
  $("#imageGallery").css("visibility", "visible");
  $("#centralDisplay").css("visibility", "visible");
});


//This function accounts for user behavior when clicking on the temperature toggle slider.
$(".slider").on("click", function (event) {
  event.stopPropagation();
  //call the backend function to change the temperature preference setting in the SessionData class
  $.ajax({
    method: "POST",
    url: "/AnalysisServlet",
    data: {
      method: "toggleTempUnit"
    }
  })
    .done(function (response) {
      console.log(response);
      //modify the temperature display on the page to display the temperature in the appropriate units.
      toggleTempUnit();
    })
    .fail(function () {
      console.log("error in AJAX request");
    });
});


//handle user clicking on the remove button
$("#removeButton").on("click", function () {
  //Check to see if there is a city selected already.
  if ($(".active").length == 0) {
    return;
  }
  else {
    //display final confirm modal to remove from favorite city list.
    $("#deleteTarget").text($(".active").find(".name").text());
    $("#deleteModal").fadeIn(300);

  }
});

//clicking the yes button to delete a city
$("#yesbutton").on("click", function () {

  //call backend to delete from fav city list
  $.ajax({
    method: "POST",
    url: "/VacationServlet",
    data: {
      method: "remFromFav",
      name: $(".active").find(".name").text(),
      lat: $(".active").find(".lat").text(),
      long: $(".active").find(".long").text(),
      username:sessionStorage.getItem("loggedInUsername")
    }
  })
    .done(function (response) {
      console.log(response);
    })
    .fail(function () {
      console.log("error in AJAX request");
    });

  //Remove the city from the DOM
  $(".active").fadeOut(400, function () {
    $(".active").remove();
  });

  //dismiss deleteModal
  $("#deleteModal").hide();

  //hide all of the slideshows and stuff
  $("#weatherWidget").css("visibility", "hidden");
  $("#imageGallery").css("visibility", "hidden");
  $("#forecastWidget").css("visibility", "hidden");
});

//clicking the cancel button to dismiss the deleteModal
$("#cancelbutton").on("click", function () {
  $("#deleteModal").hide();
});

//calls the backend to get the weather data for the city that has been clicked on.
function getAnalysis(name, lat, long) {

  console.log("getAnalysis() call here on the following data...");
  console.table([name, lat, long]);

  //make call to backend
  $.ajax({
    method: "POST",
    url: "/AnalysisServlet",
    data: {
      method: "citySearch",
      name: name,
      lat: lat,
      long: long
    }
  })
    .done(function (response) {
      console.log(response);
      handleResponse(response);
    })
    .fail(function () {
      console.log("error in AJAX request");
    });

}

//this function takes in data from backend and displays it to front end appropriatley
function handleResponse(response) {

  var responseArray = response.split("@");
  var weatherData = responseArray[0];
  var forecastData = responseArray[1];
  var galleryData = responseArray[2];

  populateWeatherWidget(weatherData);
  populateForecast(forecastData);
  populateCarousel(galleryData);
}

//"Los Angeles_heavy intensity rain_54_10d_34.05_-118.24"
function populateWeatherWidget(responseText) {
  if (responseText.includes("No Weather Data Found")) {
    $("#errorMessage").show();
    $("#weatherWidget").hide();
    return;
  }

  //parse responseText
  var tokens = responseText.split("_");
  //update the frontend accordingly

  let city = tokens[0];
  let desc = tokens[1];
  let tempinF = tokens[2];
  let icon = `http://openweathermap.org/img/wn/${tokens[3]}@2x.png`;
  console.log("icon");
  console.log(icon);


  // let lat = tokens[4];
  // let long = tokens[5];
  if ($("#tempType").text().trim() == "C") {
    let curTempF = Number.parseFloat(tempinF);
    let curTempC = Math.round(((curTempF - 32) * (5 / 9)));
    $("#tempValue").text(curTempC);
  }
  else {
    $("#tempValue").text(tempinF);
  }
  $("#cityName").text(city);
  $("#weatherDescription").text(desc);
  $("#weatherIcon").prop("src", icon);

  //show the data
  $("#errorMessage").hide();
  $("#weatherWidget").show();
  $("#weatherWidget").css("visibility", "visible");
}

function populateForecast(responseText) {

  console.log("populateing forcast....");
  //clear out the forecast widget beforehand
  $("#forecastWidget").html("");

  //parseData
  var data = responseText.split("&")
  var currDate = new Date();
  for (var i = 0; i < 5; i++) {
    if (!data[i]) continue; //check to make sure we got data from backend.
    var datapoint = data[i];
    console.log(datapoint);
    var dataItems = datapoint.split("_");

    var low = dataItems[0];
    var hi = dataItems[1];
    var icon = dataItems[2];

    //change the temps to C if necessary
    if ($("#tempType").text() == "C") {
      let curLowF = Number.parseFloat(low);
      low = Math.round(((curLowF - 32) * (5 / 9)));

      let curHiF = Number.parseFloat(hi);
      hi = Math.round(((curHiF - 32) * (5 / 9)));
    }

    var date = (currDate.getMonth() + 1) + "/" + (currDate.getDate());

    var $newItem = $("<div class='dayBox'></div>");

    $newItem.append(`<div class="dayBox_date">${date}</div>`);
    $newItem.append(`<div class="dayBox_icon"><img class = "weatherIcon" src="http://openweathermap.org/img/wn/${icon}@2x.png" alt=""></div>`);
    $newItem.append(`<div class="dayBox_high"><span class = "tempValue">${hi}</span>&#176</div>`);
    $newItem.append(`<div class="dayBox_low"><span class = "tempValue">${low}</span>&#176</div>`);

    $("#forecastWidget").append($newItem);

    currDate.setDate(currDate.getDate() + 1);
  }
  $("#forecastWidget").css("visibility", "visible");
}

//"#https://maps.googleapis.com/maps/api/place/photo?maxwidth=800&photoreference=CmRaAAAA6fyQyS2AQnctPLtMNfOW9Nm2BP3vfsx24nFGKYzTlJ7xtyCRfI631m9fvBG1B_95Qln-sKTTLEX8t1WWDT0YyC_DBCF5I9XkQGX19doBge9BvnNM9R56McRQr2TQW2zKEhCZadfo5T10QGrU0TiaDOUeGhS3sxT462g9BtXI2yegcFX2eg4Vvg&key=AIzaSyDWGBQBOZGJWD9VvWZSqe-g6bgShrSif8M#https://maps.googleapis.com/maps/api/place/photo?maxwidth=800&photoreference=CmRaAAAA5rIDHHG0YioCqsnIKAH_fYp_ZgS00k7e-X_OJ_ATlJuGb5TlgPqawxclk0EqvejF_8KommBRM1AW_-qy8pJeNb5lIFGgjNtpZxGMnoYsvFcExBzeyEyQgCTbuost0nN4EhBlhpXSCdZSyosq98IVJ2d5GhQJAdn49Bvh6DvjYNGdonk3cPy5QQ&key=AIzaSyDWGBQBOZGJWD9VvWZSqe-g6bgShrSif8M#https://maps.googleapis.com/maps/api/place/photo?maxwidth=800&photoreference=CmRaAAAAFFOX4cL60cb2PU4CXQWX0idqZDKGi_uCY3UmYhfMzGW1EQ3OTFOsvZLdeo2E3WCwWNdlIwCSLHz8EQSZGtQA2Z0l2hPB1TDs12BtcB7RQLkRvbZrgaVJxrPVjmZaSxH1EhDqN4Nru4-rZyeDhC-4Gk5iGhQLFtjQZDzZJp8moqQuOlSiVAloXw&key=AIzaSyDWGBQBOZGJWD9VvWZSqe-g6bgShrSif8M#https://maps.googleapis.com/maps/api/place/photo?maxwidth=800&photoreference=CmRaAAAAyRjOzpKUUzc1MxpwdbrcLgX5Qf0XOB6TG-SpKy5gGUCEX4ina5ql8ZRNGN9l8zqITFaq1LC-ihBoLaR2E7zByVivsKT3lAaC7tThkG5OwmeT1CQtre9lzBuqTxFtMbhMEhCtzZS9o_FL0y0oHbSaHx9KGhSI8tIihofVr7kpb8A-Oszz4YXapw&key=AIzaSyDWGBQBOZGJWD9VvWZSqe-g6bgShrSif8M#https://maps.googleapis.com/maps/api/place/photo?maxwidth=800&photoreference=CmRaAAAA4brtQ4xn0vv8cPUTHMid9tvfbZhdphY-V-zD7z58nBE-PbBGmRqYPH4iUdOdbMMmp6abL7BG27awSvWEd7USyo_JuL9TPd4rch4UCOxSM5aAqysjFv3TSJ1SFKpbqxhuEhBouR2D2WlKXfjVcQmUsy1QGhRfULqJkFenerARJuWHq4yTXmo8KA&key=AIzaSyDWGBQBOZGJWD9VvWZSqe-g6bgShrSif8M"
function populateCarousel(responseText) {

  var photoLinks = responseText.split("#");

  for (var i = 1; i <= 5; i++) {
    if (photoLinks[i]) {
      $(`#cityImage${i}`).prop("src", photoLinks[i]);
    }
    else {
      $(`#cityImage${i}`).prop("src", "https://www.wsdisplay.com/wsdisplay/img/no_image_available.jpeg?resizeid=5&resizeh=1000&resizew=1000");
    }
  }

  $("#imageGallery").css("visibility", "visible");
}