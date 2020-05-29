// Check if user is logged in upon page load
if (sessionStorage.getItem("userLoggedIn").match("false")) {
	console.log("In failed login branch");
  document.location = "index.html"
}
$("#user").text(sessionStorage.getItem("loggedInUsername"));
$(document).ready(function(){
  loadHistory();
});

// Make the text box focused upon load
$("#searchQuery").focus();

// Execute this code immediately when the page loads to get the sessionData from the backend
// and update the frontend accordingly. (Temperature Unit Preference)
var today = new Date();
var dd = String(today.getDate()).padStart(2, '0');
var mm = String(today.getMonth() + 1).padStart(2, '0'); //January is 0!
var yyyy = today.getFullYear();
today = mm + '/' + dd + '/' + yyyy;
$("#date").text(today);
var user = $("#user").text();

$.ajax({
  method: "POST",
  url: "/LandingServlet",
  data: {
    method: "initialize",
    username: sessionStorage.getItem("loggedInUsername")
  }
})
  .done(function (response) {
    console.log(response);
    if (response == "F") {
      $("#checkbox").prop("checked", true);
      $(".tempUnit").html("F");
    }
    else {
      $("#checkbox").prop("checked", false);
      $(".tempUnit").html("C");
    }
  })
  .fail(function () {
    console.log("error in AJAX request");
  });


//This function accounts for user behavior when clicking on the temperature toggle slider.
$(".slider").on("click", function (event) {
  //call the backend function to change the temperature preference setting in the SessionData class
  $.ajax({
    method: "POST",
    url: "/LandingServlet",
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

//user clicks on the search button
$("#SMTWButton").on("click", () => {
  $("#searchQuery").css("border-bottom","1px solid rgba(238, 238, 238, 0.41)");
  if (!$("#searchQuery").val()){
    $("#searchQuery").css("border-bottom","0.8px solid #FF0000");
    $("#searchQuery").attr("placeholder", "Please Enter Your Location (City/Zip)");
  }
  else{
    let searchInput = $("#searchQuery").val();
    handleSearch(searchInput);
    setTimeout(function () {
      loadHistory();
    }, 1000); 
  }
});


//This function handles a user search
function handleSearch(searchInput) {

  //call the backend to get API response
  $.ajax({
    method: "POST",
    url: "/LandingServlet",
    data: {
      method: "search",
      input: searchInput,
      username: sessionStorage.getItem("loggedInUsername")
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


//This function parses the response from backend and modifies the front end accordingly
function handleResponse(responseText) {

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

  // Show the data
  $("#errorMessage").hide();
  $("#weatherWidget").show();
  $("#weatherWidget").css("visibility", "visible");
}


function loadHistory() {
  $.ajax({
    method: "POST",
    url: "/LandingServlet",
    data: {
      method: "getHistory",
      username: sessionStorage.getItem("loggedInUsername")
    }
  })
    .done(function (response) {
      console.log(response);
      populateHistory(response);
    })
    .fail(function () {
      console.log("error in AJAX request");
    });
}

function populateHistory(responseText) {

  console.log("getting history.... with "+ user);
  console.log(responseText);
  // Clear out the searchHistory widget beforehand
  $("#searchHistory").html("");
  
  // Parse Data
  var data = responseText.split("&")
  for (var i = 0; i < 4; i++) {
    if (!data[i]) continue; //check to make sure we got data from backend.
    var datapoint = data[i];
    console.log(datapoint);
    var dataItems = datapoint.split("_");

    var city = dataItems[0];
    var temp = dataItems[1];
    var icon = dataItems[2];

    // Change the temperature to C if necessary
    if ($("#tempType").text() == "C") {
      let curTempF = Number.parseFloat(temp);
      temp = Math.round(((curTempF - 32) * (5 / 9)));
    }

    var $newItem = $("<div class='dayBox'></div>");

    $newItem.append(`<div class="dayBox_city" id="hisCity`+i+`">${city}</div>`);
    $newItem.append(`<div class="dayBox_icon"><img class = "weatherIcon" src="${icon}" alt=""></div>`);
    $newItem.append(`<div class="dayBox_high"><span class = "tempValue">${temp}</span>&#176<span class="tempUnit" id="cctempUnit">F</span></div>`);

    $("#searchHistory").append($newItem);
  }
}

$("#searchHistory").on("click", ".dayBox", function () {
  //mark the clicked city as active
  var location = $(this).find(".dayBox_city").text();
  console.log("search for "+ location);
  handleSearch(location);
  setTimeout(function () {
    loadHistory();
  }, 1000); 
})