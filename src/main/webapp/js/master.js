
// Navigation bar functionality
//#######################################################
$("#homeButton").on("click", () => {
  if (document.location.href.search("home.html") == -1)
    document.location = "home.html";
});

$("#vacationButton").on("click", () => {
  if (document.location.href.search("vacation.html") == -1)
    document.location = "vacation.html";
});

$("#activityButton").on("click", () => {
  if (document.location.href.search("activity.html") == -1)
    document.location = "activity.html";
});

$("#analysisButton").on("click", () => {
  if (document.location.href.search("analysis.html") == -1)
    document.location = "analysis.html";
});

$("#signoutButton").on("click", () => {
  sessionStorage.setItem("userLoggedIn", false);
  sessionStorage.setItem("loggedInUsername", "");
  document.location = "index.html";
});

//########################################################

function toggleTempUnit() {
  if ($("#tempType").text().trim() == "C")
    convertToF();
  else
    convertToC();
}

function convertToF() {
  console.log("Converting page to F");
  $(".tempValue").each(function (index) {
    let curTempC = Number.parseFloat($(this).text());
    let curTempF = Math.round(((curTempC * 9) / 5) + 32);
    $(this).text(curTempF);
    $(".tempUnit").html("F");
  })
}

function convertToC() {
  console.log("converting page to C");
  $(".tempValue").each(function (index) {
    let curTempF = Number.parseFloat($(this).text());
    let curTempC = Math.round(((curTempF - 32) * (5 / 9)));
    $(this).text(curTempC);
    $(".tempUnit").html("C");
  });
}
