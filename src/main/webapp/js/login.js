 // Upon successful login, set sessionStorage globally for successful login
 sessionStorage.setItem("userLoggedIn", false);
 sessionStorage.setItem("loggedInUsername", null);
 $("#login").on("click", () => {
		$("#username").css("border-bottom","1px solid rgba(238, 238, 238, 0.41)");
	 if (!$("#username").val()){
	  $("#username").css("border-bottom","0.8px solid #FF0000");
	  $("#username").attr("placeholder", "Please Enter Your Username");
	 }
	 $("#password").css("border-bottom","1px solid rgba(238, 238, 238, 0.41)");
	 if (!$("#password").val()){
	  $("#password").css("border-bottom","0.8px solid #FF0000");
	  $("#password").attr("placeholder", "Please Enter Your Password");
	 }
	 if ($("#username").val() && $("#password").val()){
		handleLogin();
	  console.log("try login");
	 }
	 });
$("#register").on("click", () => {
		$("#usernameReg").css("border-bottom","1px solid rgba(238, 238, 238, 0.41)");
	 if (!$("#usernameReg").val()){
	  $("#usernameReg").css("border-bottom","0.8px solid #FF0000");
	  $("#usernameReg").attr("placeholder", "Please Enter Your Username");
	 }
	 $("#passwordReg").css("border-bottom","1px solid rgba(238, 238, 238, 0.41)");
	 if (!$("#passwordReg").val()){
	  $("#passwordReg").css("border-bottom","0.8px solid #FF0000");
	  $("#passwordReg").attr("placeholder", "Please Enter Your Password");
	 }
	 $("#passwordCheck").css("border-bottom","1px solid rgba(238, 238, 238, 0.41)");
	 if (!$("#passwordCheck").val()){
	  $("#passwordCheck").css("border-bottom","0.8px solid #FF0000");
	  $("#passwordCheck").attr("placeholder", "Please Re-Enter Your Password");
	 }
	 if ($("#usernameReg").val() && $("#passwordReg").val() && $("#passwordCheck").val()){
		 handleRegistration();
	  console.log("try login");
	 }
		});

  function handleLogin() {
    // Capture form data from HTML login.html
    let loginUsername = $("#username").val();
    let loginPassword = $("#password").val();
    console.log("Login username: " + loginUsername);
  
    // AJAX POST request to /LoginServlet
    $.ajax({
      method: "POST",
      url: "/LoginServlet",
      data: {
        requestedAction: "login",
        username: loginUsername,
        password: loginPassword
      }
    })
      .done(function (response) {
        console.log(response);
        handleResponse(response, "loginResponse");
      })
      .fail(function () {
        console.log("Error in AJAX request to login servlet");
      });
  }
  
  function handleRegistration() {
    // Capture form data from login.html
    let registerUsername = $("#usernameReg").val();
    let registerPassword = $("#passwordReg").val();
    let registerReenterPassword = $("#passwordCheck").val();
  
    // AJAX POST request to /LoginServlet
    $.ajax({
      method: "POST",
      url: "/LoginServlet",
      data: {
        requestedAction: "register",
        username: registerUsername,
        password: registerPassword,
        reenterPassword: registerReenterPassword
      }
    })
      .done(function (response) {
        console.log(response);
        handleResponse(response, "registrationResponse");
      })
      .fail(function () {
        console.log("Error in AJAX request to login servlet");
      });
  }
  
  // Parses response from backend & modifies the frontend accordingly
  function handleResponse(responseText, responseType) {
    // Distinguish between login response and registration response
    console.log ("Response text:" + responseText);
    if (responseType == "loginResponse") {
      if (responseText.includes("user logged in successfully")) {
    	  // Upon successful login, set sessionStorage globally for successful login
        sessionStorage.setItem("userLoggedIn", true);
        sessionStorage.setItem("loggedInUsername", $("#username").val());
        document.location = "home.html";
      }
      else {
        $('.errorMsg').css("display", "inline-block");
        document.getElementById("errorMsg").innerHTML = responseText.trim();
      }
    }
    else {
      if (responseText.includes("user registered successfully")) {
    	  // Upon successful login, set sessionStorage globally for successful login
          sessionStorage.setItem("userLoggedIn", true);
          sessionStorage.setItem("loggedInUsername", $("#username").val());
        document.location = "home.html";
      }
      else {
        $('.errorMsgReg').css("display", "inline-block");
        document.getElementById("errorMsgReg").innerHTML = responseText.trim();
      }
    }
    return;
  } 
