$(function () {
  var inputs = document.getElementsByTagName("input");
  inputs[0].value = USER_INFO.userID;
  inputs[1].value = USER_INFO.password;

  document.getElementById("login").onclick = () => {
    $(".hint span").css("display", "none");
    if (inputs[0].value != "" && inputs[1].value != "") {
      //校验,登录
      $.ajax({
        url: AJAX_URL.login,
        data: JSON.stringify({
          "account": inputs[0].value,
          "password": inputs[1].value,
        }),
        contentType:"application/json",
        type: "POST",
        dataType: "json",
        xhrFields:{withCredentials: false},
        success: (data) => {
          $("#login_word").removeClass("to_none");
          $("#reg_wait").css("display", "none");
          if (data.type == false) {
            //展示错误信息
            $(".hint span").css("display", "inline");
          } else {
            USER_INFO.name = data.name;
            USER_INFO.userID = inputs[0].value;
            USER_INFO.sign = data.info;
            USER_INFO.company = data.company;
            USER_INFO.address = data.address
            localStorage.setItem("userName", USER_INFO.userID);
            localStorage.setItem("name", USER_INFO.name);
            localStorage.setItem("sign", USER_INFO.sign);
            localStorage.setItem("company", USER_INFO.company);
            localStorage.setItem("address", USER_INFO.address);
            window.open("../pages/index.html", "_self");
            //console.log(localStorage.getItem("lastname"))
          }
        },
        error: () => {
          $("#login_word").removeClass("to_none");
          $("#reg_wait").css("display", "none");
          alert("啊哦，网络可能出了些错误，请稍后重试");
            localStorage.setItem("userName", "");
            localStorage.setItem("name", "");
            localStorage.setItem("sign", "");
            localStorage.setItem("company", "");
            localStorage.setItem("address", "");
        },
      });
      $("#login_word").addClass("to_none");
      $("#reg_wait").css("display", "inline-block");
    } else {
      if (inputs[0].value == "")
        inputs[0].style.borderBottom = "3px solid #B22222";
      if (inputs[1].value == "")
        inputs[1].style.borderBottom = "3px solid #B22222";
    }
  };

});


