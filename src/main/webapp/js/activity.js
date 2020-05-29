// Check if user is logged in upon page load
if (sessionStorage.getItem("userLoggedIn").match("false")) {
	console.log("In failed login branch");
  document.location = "index.html"
}

// Global Variables
var list = new Array();
var pageList = new Array();
var currentPage = 1;
var numberPerPage = 5;
var numberOfPages = 1;   // calculates the total number of pages
var numCities;
var tempUnit = $("#tempType").text();
var cities;
var sortLike = true;
var sortDis = true;

// Make the range lower text box focused upon document load
$("#input_activity").focus();

// Execute upon page load to getsessionData from back end and update the
// front end accordingly (temperature unit preference)
$.ajax({
  method: "POST",
  url: "/ActivityServlet",
  data: {
    method: "initialize"
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
initFavButton();

// Sends temperature toggle data to back end upon click event firing on toggle
$(".slider").on("click", function (event) {
  // Stop the click event from bubbling to parent elements
  event.stopPropagation();

  // POST request to /ActivityServlet to toggle temperature unit in session
  // data
  $.ajax({
    method: "POST",
    url: "/ActivityServlet",
    data: {
      method: "toggleTempUnit"
    }
  })
    .done(function (response) {
      console.log(response);
      // Modify all displayed temperature value strings to proper unit
      // (master.js)
      toggleTempUnit();
    })
    .fail(function () {
      console.log("Error in AJAX request to toggle the temperate unit");
    });
});

// Handles search when the click event on the search button is fired
$("#find_result").on("click", function () {
  $("#input_activity").css("border-bottom","1px solid rgba(238, 238, 238, 0.41)");
  $("#input_radius").css("border-bottom","1px solid rgba(238, 238, 238, 0.41)");
  $("#errorMessage").text("");
  $(".columnHeadings").hide();
  $("#buttons").hide();
  $("#resultbox").css("visibility","visible");
  if (!$("#input_activity").val()){
    $("#input_activity").css("border-bottom","0.8px solid #FF0000");
    $("#errorMessage").append("<h3>Invalid Input for Activity</h3>");
    $("#errorMessage").show();
  }
  if (!$("#input_radius").val()){
    $("#input_radius").css("border-bottom","0.8px solid #FF0000");
    $("#errorMessage").append("<h3>Invalid Input for Radius</h3>");
    $("#errorMessage").show();
  }
  if ($("#input_activity").val() && $("#input_radius").val()){
    $(".columnHeadings").show();
    $("#buttons").show();
    handleSearch();
  }
});

// Reorders distance table upon click event being fired on distance heading
$("#distanceHeading").on("click", function () {
  if (sortDis){
    cities.sort(function(a,b){
      return b[7] - a[7];
    });
    sortDis = false;
  }
  else{
    cities.sort(function(a,b){
      return a[7] - b[7];
    });
    sortDis = true;
  }

  initFavButton();
  load();
  showPageNum();
});

$("#likesHeading").on("click",function(){
  for (i = 0; i < numCities; i++){
    //var data = cityString[i].split("_");
    var likes = getlikes(cities[i][0]);
    cities[i][cities[i].length-1] = likes;
  }
  if (sortLike){
    cities.sort(function(a,b){
      return b[b.length-1] - a[a.length-1];
    });
    //sortLike = false;
  }
  else{
    cities.sort(function(a,b){
      return a[a.length-1] - b[b.length-1];
    });
    sortLike = true;
  }
  initFavButton();
  load();
  showPageNum();
});

function initFavButton() {
  // Handle click on an add to favorites button
  $(".rowdata").on("click", ".addToFav", function (event) {
    event.stopPropagation();
    var method = "";
    // Change the text to show remove from favorites or vice versa
    if ($(this).html().search("Add") == -1) {
      // Update button text
      $(this).html("Add to Favorites");
      $(this).css("background-color","rgb(151, 148, 148)");
      method = "remFromFav";
    }
    else {
      // Update button text
      $(this).html("Remove from Fav");
      $(this).css("background-color","rgb(247, 75, 75)");
      method = "addFav";
    }

    // Call back end code to add/remove item from favorites list.
    var name = $(this).parent().find(".name").text();
    var country = $(this).parent().find(".country").text();
    var lat = $(this).parent().find(".lat").text();
    var long = $(this).parent().find(".long").text();

    console.table([name, country, lat, long]);

    console.log("method: " + method);

    $.ajax({
      method: "POST",
      url: "/VacationServlet",
      data: {
        method: method,
        name: name,
        country: country,
        lat: lat,
        long: long,
        username:sessionStorage.getItem("loggedInUsername")
      }
    })
      .done(function (response) {
        console.log(response);
        // Modify the temperature display on the page to display
        // the temperature in the appropriate units.
      })
      .fail(function () {
        console.log("Error in AJAX request");
      });

    
  });


  $(".rowdata").on("click", ".likesup", function (event) {
    event.stopPropagation();
    // Change the text to show remove from favorites or vice versa
    var currLike = $(this).parent().text();
    currLike ++;
    var name = $(this).parent().parent().find(".name").text();
    updatelikes(name,currLike);
    // var prevLike = $(this).parent().parent().prev().find(".numlikes").text();
    // var curr = $(this).parent().parent();
    // var prev = curr.prev();
    $(this).parent().html(`<i class="fas fa-chevron-up likesup"></i>${currLike}<i class="fas fa-chevron-down likesdown"></i>`);
    // console.log("PREVIOUS LIKE is: "+prevLike);
    // if (prevLike < currLike && prevLike!=""){
    //   console.log("SWAPPING");
    //   curr.swapWith(prev);
    // }
    // Call back end code to add/remove item from favorites list.
    console.log("updating likes for "+name+" as "+currLike);
  });

  $(".rowdata").on("click", ".likesdown", function (event) {
    event.stopPropagation();
    // Change the text to show remove from favorites or vice versa
    var currLike = $(this).parent().text();
    if (currLike != 0){
      currLike--;
    }
    var name = $(this).parent().parent().find(".name").text();
    updatelikes(name,currLike);
    // var nextLike = $(this).parent().parent().next().find(".numlikes").text();
    // var curr = $(this).parent().parent();
    // var next = curr.next();
    $(this).parent().html(`<i class="fas fa-chevron-up likesup"></i>${currLike}<i class="fas fa-chevron-down likesdown"></i>`);
    // console.log("PREVIOUS LIKE is: "+nextLike);
    // if (nextLike > currLike && nextLike!=""){
    //   console.log("SWAPPING");
    //   curr.swapWith(next);
    // }
    // Call back end code to add/remove item from favorites list.
    console.log("updating likes for "+name+" as "+currLike);
  });
}


// This function is called when the user clicks on the search button.
function handleSearch() {
  // Make the results table container visible
  $("#resultbox").show();

  // Reset any existing error statuses of input fields
  $("#errorMessage").html("");
  
  $("#input_activity").removeClass("errorHighlight");
  $("#input_num_results").removeClass("errorHighlight");
  $("#input_location").removeClass("errorHighlight");
  
// Front input validation
  let activity = $("#input_activity").val();
  let browserLatitude = $("#lat").text();
  let browserLongitude = $("#long").text();
  // UPDATE - ADDITIONAL RADIUS INPUT for project 2
  let radius = $("#input_radius").val();
  if(browserLatitude == ""){
	  browserLatitude = 38;
  }
  if(browserLongitude == ""){
	  browserLongitude = -122;
  }
  console.log("browserLatitude:" + browserLatitude);
  console.log("browserLongitude:" + browserLongitude);

  /* Front-end input validation
  let caughtError = false;
  if (!activity) {
    caughtError = true;
    $("#input_activity").addClass("errorHighlight");
    $("#errorMessage").append("<h3>illegal value for input Activity</h3>");
  }

  if (!browserLatitude || !browserLongitude) {
    caughtError = true;
    $("#input_location").addClass("errorHighlight");
    $("#errorMessage").append("<h3>illegal value for browser Location</h3>");
  }

  if (!radius) {
    caughtError = true;
    $("#input_activity").addClass("errorHighlight");
    $("#errorMessage").append("<h3>illegal value for input Radius</h3>");
  }

  if (caughtError) {
    $("#errorMessage").show();
    $(".columnHeadings").hide();
    $(".resultsTable").hide();
    return;
  }*/

  // Set error highlights if error was caught with input
  $("#input_activity").removeClass("errorHighlight");
  $("#input_num_results").removeClass("errorHighlight");
  $("#input_location").removeClass("errorHighlight");

  // AJAX query to /ActivityServlet
  $.ajax({
    method: "POST",
    url: "/ActivityServlet",
    data: {
      method: "search",
      activity: activity,
      browserLocationLatitude: browserLatitude,
      browserLocationLongitude: browserLongitude,
      radius: radius
    }
  })
    .done(function (response) {
      console.log(response);
      handleResponse(response);
    })
    .fail(function () {
      console.log("Error in AJAX request");
    });
}

// Parses the response text from the back end to display on the front end
function handleResponse(responseText) {

  // ERROR CHECKING - response text includes error message
  if (responseText.includes("Error") || responseText.includes("No Weather Data Found")) {
    if (responseText.includes("No Weather Data Found")) {
      $("#errorMessage").append("<h3>No locations found.</h3>");
    }
    else {
      var errorType = responseText.split(":")[1];
      if (errorType == "Location") {
        $("#input_location").addClass("errorHighlight");
        $("#errorMessage").append("<h3>Invalid Input for Location</h3>");
      }
      if (errorType == "Activity") {
        $("#input_activity").addClass("errorHighlight");
        $("#errorMessage").append("<h3>Invalid Input for Activity</h3>");
      }
    }
    $("#errorMessage").show();
    $(".columnHeadings").hide();
    $(".resultsTable").hide();
    return;
  }
  
// Parse the table results in the response text
  // and display them on front end
  cityString = responseText.split("&");
  cities = [];
  numCities = cityString.length;
  for (i = 0; i < numCities; i++){
    var data = cityString[i].split("_");
    var likes = "";
    var fav = "";
    data.push(fav,likes);
    cities.push(data);
  }
  // cities.sort(function(a,b){
  //   return b[b.length-1] - a[a.length-1];
  // });

  initFavButton();
  load();
  showPageNum();
  console.log("got here");

  $("#errorMessage").hide();
  $(".columnHeadings").show();
  // $(".resultsTable").show();
  if(numCities > 0){
	  $(".resultsTable").show();
	  document.getElementById("resultbox").style.visibility = "visible";
	  // $("#resultbox").show();
  }
}


function makeList() {
  list = [];
  for (x = 0; x < numCities; x++) {
    list.push(cities[x]);
  }
  numberOfPages = getNumberOfPages();
  console.log("numberOfPages" + numberOfPages);
}

function getNumberOfPages() {
  return Math.ceil(list.length / numberPerPage);
}

function nextPage() {
	currentPage = +currentPage +1;
  loadList(currentPage);
}

function previousPage() {
  currentPage -= 1;
  loadList(currentPage);
}

function firstPage() {
  currentPage = 1;
  loadList(currentPage);
}

function lastPage() {
  currentPage = numberOfPages;
  loadList(currentPage);
}

function loadList(page) {
	currentPage = page;
  var begin = ((page - 1) * numberPerPage);
  console.log("begin is: " + begin);
  var end = begin + numberPerPage;
  console.log("end is: " + end);
  pageList = list.slice(begin, end);

  drawList();
  check();
  showPageNum();
  initFavButton();
}

function drawList() {
  document.getElementById("resultsTable").innerHTML = "";
  let newResultsTable = ``;
  // var len = pageList[0].length;
  // for (r = 0; r < pageList.length; r++) {
  //   pageList[r][len-1] = getlikes(pageList[r][0]);
  //   pageList[r][len-2] = getFav(pageList[r][0]);
  // }
  // pageList.sort(function(a,b){
  //   return b[b.length-1] - a[a.length-1];
  // })
  for (r = 0; r < pageList.length; r++) {
    var data = pageList[r];
    console.log("DATA IS!!!!!: " + data);
    var tempUnit = $("#tempType").text();

    // Convert to appropriate unit type
    if (tempUnit == 'C') {
      let lowInF = Number.parseFloat(data[3]);
      data[3] = Math.round(((lowInF - 32) * (5 / 9)));

      let hiInF = Number.parseFloat(data[4]);
      data[4] = Math.round(((hiInF - 32) * (5 / 9)));
    }

    let likes = getlikes(data[0]);
    let fav = getFav(data[0]);
    //console.log("LIKES&FAVORITE STATUS: "+ data[0] + " likes: "+likes+" favorites:" + fav);

    var buttonText = "";
    if (fav == "Checked if Fav: result->true") {
      buttonText = "Remove from Fav";
      var newRowData =
      `
        <div class="rowdata">
        	<div class="rowEntry name">${data[0]}</div>
        	<div class="rowEntry country">${data[1]}</div>
        	<div class="rowEntry"><div><span class="tempValue">${data[2]}</span>&#176<span class="tempUnit">${tempUnit}</div></div>
	        <div class="rowEntry"><span class = "dist">${data[7]}</span> miles</div>
	        <div class="rowEntry addToFav" style="background-color:rgb(247, 75, 75)">${buttonText}</div>
          <div class="rowEntry numlikes">
          <i class="fas fa-chevron-up likesup"></i>${likes}
          <i class="fas fa-chevron-down likesdown"></i>
          </div>
          <div class="lat" style="display: none">${data[5]}</div>
	        <div class="long" style="display: none">${data[6]}</div>
        </div>
        `
    } else {
      buttonText = "Add to Favorites";
      var newRowData =
      `
        <div class="rowdata">
        	<div class="rowEntry name">${data[0]}</div>
        	<div class="rowEntry country">${data[1]}</div>
        	<div class="rowEntry"><div><span class="tempValue">${data[2]}</span>&#176<span class="tempUnit">${tempUnit}</div></div>
	        <div class="rowEntry"><span class = "dist">${data[7]}</span> miles</div>
          <div class="rowEntry addToFav">${buttonText}</div>
          <div class="rowEntry numlikes">
          <i class="fas fa-chevron-up likesup"></i>${likes}
          <i class="fas fa-chevron-down likesdown"></i>
          </div>
	        <div class="lat" style="display: none">${data[5]}</div>
	        <div class="long" style="display: none">${data[6]}</div>
        </div>
        `
    }

    newResultsTable += newRowData;

  }
  $("#resultsTable").html(newResultsTable);
  $(".resultsTableContainer").css("visibility", "visible");
}

function check() {
  document.getElementById("next").disabled = currentPage == numberOfPages ? true : false;
  document.getElementById("previous").disabled = currentPage == 1 ? true : false;
}

function showPageNum(){
	console.log("PRINT CURR PG AGAIN!!!!!!!!!!!!!: " + currentPage);
	document.getElementById("pagination").innerHTML = "";
	if(currentPage > 2 && currentPage < numberOfPages - 1){
		for (var j = (+currentPage - +2); j <= (+currentPage + +2); j++) {
			if(j == currentPage){
				console.log("CASE 1");
				var id = "pagenum" + j;
				document.getElementById("pagination").innerHTML += '<class="pages active" id=' + id + '>' + j + ' </a>';
				
				// document.getElementById("pages").innerHTML += '<div
				// class="pagenum" id="pag" value="' + j + '">' + j + ' </div>';
				document.getElementById(id).value = id.slice(-1);
				
				// document.getElementById(id).onclick = loadList(id.slice(-1));
				document.getElementById(id).addEventListener("click", myFunction, false);
				console.log("value is ::::" + document.getElementById(id).value);
			}
			else{
				console.log("CASE 2 " + (+currentPage - +2) + " " + (+currentPage + +2));
				var id = "pagenum" + j;
				document.getElementById("pagination").innerHTML += '<a href="#" onclick="loadList(' + j + ')" id=' + id + '>' + j + ' </a>';
				// document.getElementById("pages").innerHTML += '<div
				// class="pagenum" id="pag" value="' + j + '">' + j + ' </div>';
				document.getElementById(id).value = id.slice(-1);
				
				// document.getElementById(id).onclick = loadList(id.slice(-1));
				document.getElementById(id).addEventListener("click", myFunction, false);
				console.log("value is ::::" + document.getElementById(id).value);
			}
			
		}
	}
	else if(currentPage < 3){ // account for first 2 pages or last 2 pages
		if(numberOfPages >= 5){ // more than 5 pages
			console.log("CASE 3");
			for (var j = 1; j <= 5; j++) {
				var id = "pagenum" + j;
				if(j == currentPage){
					document.getElementById("pagination").innerHTML += '<class="pages active" id=' + id + '>' + j + ' </a>';
				}
				else{
					document.getElementById("pagination").innerHTML += '<a href="#" onclick="loadList(' + j + ')" id=' + id + '>' + j + ' </a>';
				}
				// document.getElementById("pages").innerHTML += '<div
				// class="pagenum" id="pag" value="' + j + '">' + j + ' </div>';
				document.getElementById(id).value = id.slice(-1);
				
				// document.getElementById(id).onclick = loadList(id.slice(-1));
				document.getElementById(id).addEventListener("click", myFunction, false);
				console.log("value is ::::" + document.getElementById(id).value);
			}
		}
		else{
			console.log("CASE 4");
			for (var j = 1; j <= numberOfPages; j++) { // accounts for first 2
														// pages if < 5 pages
														// --> prints 1-5
				var id = "pagenum" + j;
				if(j == currentPage){
					document.getElementById("pagination").innerHTML += '<class="pages active" id=' + id + '>' + j + ' </a>';
				}
				else{
					document.getElementById("pagination").innerHTML += '<a href="#" onclick="loadList(' + j + ')" id=' + id + '>' + j + ' </a>';
				}
				document.getElementById(id).value = id.slice(-1);
				
				// document.getElementById(id).onclick = loadList(id.slice(-1));
				document.getElementById(id).addEventListener("click", myFunction, false);
				console.log("value is ::::" + document.getElementById(id).value);
			}
		}
	}
	else{ // account for last 2 pages
		console.log("CASE 5");
		if(numberOfPages <= 4){
			for (var j = 1; j <= numberOfPages; j++) {
				var id = "pagenum" + j;
				if(j == currentPage){
					document.getElementById("pagination").innerHTML += '<class="pages active" id=' + id + '>' + j + ' </a>';
				}
				else{
					document.getElementById("pagination").innerHTML += '<a href="#" onclick="loadList(' + j + ')" id=' + id + '>' + j + ' </a>';
				}
				// document.getElementById("pages").innerHTML += '<div
				// class="pagenum" id="pag" value="' + j + '">' + j + ' </div>';
				document.getElementById(id).value = id.slice(-1);
				
				// document.getElementById(id).onclick = loadList(id.slice(-1));
				document.getElementById(id).addEventListener("click", myFunction, false);
				console.log("value is ::::" + document.getElementById(id).value);
			}
		}
		else {
			for (var j = numberOfPages - 4; j <= numberOfPages; j++) {
				var id = "pagenum" + j;
				if(j == currentPage){
					document.getElementById("pagination").innerHTML += '<class="pages active" id=' + id + '>' + j + ' </a>';
				}
				else{
					document.getElementById("pagination").innerHTML += '<a href="#" onclick="loadList(' + j + ')" id=' + id + '>' + j + ' </a>';
				}
				// document.getElementById("pages").innerHTML += '<div
				// class="pagenum" id="pag" value="' + j + '">' + j + ' </div>';
				document.getElementById(id).value = id.slice(-1);
				
				// document.getElementById(id).onclick = loadList(id.slice(-1));
				document.getElementById(id).addEventListener("click", myFunction, false);
				console.log("value is ::::" + document.getElementById(id).value);
			}
		}
		
	}
	
}
function getFav(cityName){
  var result = $.ajax({
    method: "POST",
    url: "/VacationServlet",
    async:false,
    data: {
      method: "isFav",
      name: cityName,
      username:sessionStorage.getItem("loggedInUsername")
    }
  })
    .done(function (response) {
      console.log(cityName + "with "+ response +"(favorite)");
      return(response);
    })
    .fail(function () {
      console.log("error in AJAX request");
    });
  return(result.responseText);
}

function getlikes(cityName){
  var result = $.ajax({
    method: "POST",
    url: "/VacationServlet",
    async:false,
    data: {
      method: "getLikes",
      name: cityName,
      username:sessionStorage.getItem("loggedInUsername")
    }
  })
    .done(function (response) {
      console.log(cityName + "with "+ response +"likes");
      return(response);
    })
    .fail(function () {
      console.log("error in AJAX request");
    });
    return(result.responseText);
}

function updatelikes(cityName,likes){
  $.ajax({
    method: "POST",
    url: "/VacationServlet",
    async:false,
    data: {
      method: "incrementLikes",
      name: cityName,
      likes: likes,
      username:sessionStorage.getItem("loggedInUsername")
    }
  })
    .fail(function () {
      console.log("error in AJAX request");
    });
}


$(function() {
  $('#likesHeading').click(function() {
      var liContents = [];
      $('.rowdata').each(function() {
          liContents.push($(this).find(".likes").text());
          // console.log("SORTING:" + liContents);
      });
      liContents.sort(numOrdDesc);
      $('.rowdata').each(function() {
          $(this).html(liContents.pop());
      });
      initFavButton();
      load();
  });
});

function numOrdDesc(a, b) {
  a.trim();
  b.trim();
  console.log("$$$$$compareing "+a+" "+b);
  return (parseInt(b) - parseInt(a));
}

function myFunction(e) {
  currentPage = this.id.slice(-1);
  loadList(currentPage);
}

function load() {
  makeList();
  currentPage = 1;
  loadList(currentPage);
}

jQuery.fn.swapWith = function(to) {
  return this.each(function() {
      var copy_to = $(to).clone(true);
      var copy_from = $(this).clone(true);
      $(to).replaceWith(copy_from);
      $(this).replaceWith(copy_to);
  });
};