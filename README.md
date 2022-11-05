# Sakamichi app

## How to get this App
------------

* [Link](https://kokoichi0206.mydns.jp/sakamichi-app.apk)
* QR Code

![](./readme/link_to_apk.png)


## Views
------------

| Screen |  |
|:-----|:---------:|
| <br>**Member List Screen**<br><br>You can look at the member list.<br><br> • Compare the three groups<br> • Get detailed information by tapping the image<br> • Sort members by birthday, generation, etc.<br><br>**[> Browse](app/src/main/java/jp/mydns/kokoichi0206/sakamichiapp/presentation/member_list/)** | <img src="readme/screenshots/member_list.png" width="160"> |  
| <br>**Detailed Page Screen**<br><br>You can get the detailed information of a specific member.<br><br> • Tags<br> • Height<br> • Birthday<br> • Blood type<br><br>**[> Browse](app/src/main/java/jp/mydns/kokoichi0206/sakamichiapp/presentation/member_list/)** | <img src="readme/screenshots/detailed.png" width="160"> |  
| <br>**Blog Screen**<br><br>You can check the image and the updated datetime of the latest blog.<br><br> • The displayed date is updated every day at midnight<br>• Sort by name or updated time<br> • Tap to go to the detailed page<br><br>**[> Browse](app/src/main/java/jp/mydns/kokoichi0206/sakamichiapp/presentation/blog/)** | <img src="readme/screenshots/blog.png" width="160"> |  
| <br>**Position Screen**<br><br>You can get formations of 日向坂46's songs<br><br> **[> Browse](app/src/main/java/jp/mydns/kokoichi0206/sakamichiapp/presentation/positions/)** | <img src="readme/screenshots/position.png" width="320"> |  
| <br>**Quiz Screen**<br><br>You can take a quiz about the members.<br><br> • Four kinds of quizzes<br><br>**[> Browse](app/src/main/java/jp/mydns/kokoichi0206/sakamichiapp/presentation/quiz/)** | <img src="readme/quiz.gif" width="160"> |  
| <br>**Setting Screen**<br><br>In this screen, you can...<br> • Update blogs anytime<br> • Check your quiz results<br> • Clear cache of your phone<br> • Report issues to us<br> • Set your custom theme<br> • Share this app to other people<br><br>**[> Browse](app/src/main/java/jp/mydns/kokoichi0206/sakamichiapp/presentation/setting/)** | <img src="readme/settings.gif" width="160"> |  

## Directory Structure

Especially about module structures.

<pre>
.
├── app                 : main application
├── build.gradle
├── core                : core modules
│   ├── common
│   ├── data
│   ├── domain
│   └── model
├── feature             : modules separated by views
│   ├── blog
│   ├── member_detail
│   ├── member_list
│   ├── positions
│   ├── quiz
│   └── settings
├── settings.gradle
└── versions.gradle
</pre>

