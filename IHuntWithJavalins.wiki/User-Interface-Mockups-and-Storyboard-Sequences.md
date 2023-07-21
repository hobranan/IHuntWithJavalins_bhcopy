# User Interface Screens and Descriptions
* Diagrams of the different layouts of the application's user interface (and dialogs); includes comments describing the important elements. The diagrams have references to the relevant requirements and show how they are all covered.

![](https://github.com/CMPUT301W23T03/IHuntWithJavalins/blob/3e7d72b6d55ce855a2d05850a691728af92df46b/doc/images/First_screen.png)
* (Above picture): This is the first screen that will be displayed when app is run! (image company logo from website [[1](https://github.com/CMPUT301W23T03/IHuntWithJavalins/wiki/References--Citations--Licenses)])

![](https://github.com/CMPUT301W23T03/IHuntWithJavalins/blob/7c9864e981b8ffbeb09b00c5c9eaf3b5937ee24f/doc/New_Images/screenshot_signup4.png)
![](https://github.com/CMPUT301W23T03/IHuntWithJavalins/blob/7c9864e981b8ffbeb09b00c5c9eaf3b5937ee24f/doc/New_Images/screenshot_login4.png)
* (Above picture, left): 2. Signup page -> Signing up with Email, Username, password and Region (e.g. Edmonton, AB) <US 04.01.01>
* (Above picture, right): 3. Login page -> Logging in with Username and Password <US 04.01.01> 
* Once signed up or logged in, this page will skipped after opening app again <US 04.02.01>
* For the sake of our project, we are forgoing the password part

![](https://github.com/CMPUT301W23T03/IHuntWithJavalins/blob/7c9864e981b8ffbeb09b00c5c9eaf3b5937ee24f/doc/New_Images/screenshot_quicknav4.png)
![](https://github.com/CMPUT301W23T03/IHuntWithJavalins/blob/7c9864e981b8ffbeb09b00c5c9eaf3b5937ee24f/doc/New_Images/screenshot_camerascanning4.png)
1. This screen is the landing page once logged in. This is where the users name, total points till now, and total codes scanned showed. By clicking the appropriate button we can navigate to other app features _(described later)_. <US 01.02.01, US 01.05.01, US 01.06.01>
2. 'Scan Code' can take picture of QR code ('grey rectangle with white-gridlines' represents the camera's view). <US 01.01.01> (barcode image reference: [[2](https://github.com/CMPUT301W23T03/IHuntWithJavalins/wiki/References--Citations--Licenses)])

![](https://github.com/CMPUT301W23T03/IHuntWithJavalins/blob/8da78c027c6d1f492443ddbb6c80cdb97ae22852/doc/New_Images/valid_code.png)
![](https://github.com/CMPUT301W23T03/IHuntWithJavalins/blob/94ab38206e518e613f5f1bbb3e1e221eee8c6e26/doc/New_Images/generated_pic.png)
1. This page indicated the QR code scanned is 'new' code for the user <US 02.01.01>
2. This page indicated the QR code scanned is an 'already caught' code for the user.

![](https://github.com/CMPUT301W23T03/IHuntWithJavalins/blob/7c9864e981b8ffbeb09b00c5c9eaf3b5937ee24f/doc/New_Images/screenshot_commenting4.png)
![](https://github.com/CMPUT301W23T03/IHuntWithJavalins/blob/7c9864e981b8ffbeb09b00c5c9eaf3b5937ee24f/doc/New_Images/screenshot_commentingConfirm4.png)
![](https://github.com/CMPUT301W23T03/IHuntWithJavalins/blob/fa4caa8639d391c769a4103b9f88b95fb5cb0100/doc/new/removie_code.png)
1. This page shows the captured code and information regarding name, points, and comments on it. <US 02.02.01, US 02.04.01> (code doesn't record actual text scanned <US 08.01.01>)
2. User can added comments.
3. User can delete code.<US 01.03.01, US 09.01.01>

![](https://github.com/CMPUT301W23T03/IHuntWithJavalins/blob/7c9864e981b8ffbeb09b00c5c9eaf3b5937ee24f/doc/New_Images/screenshot_map4.png)
1. This page shows us where the users current location is (Purple) and shows the locations of other QR codes (Orange) <US 05.02.01, US 06.01.01, US 08.02.01> (map image reference [[4](https://github.com/CMPUT301W23T03/IHuntWithJavalins/wiki/References--Citations--Licenses)])

![](https://github.com/CMPUT301W23T03/IHuntWithJavalins/blob/7c9864e981b8ffbeb09b00c5c9eaf3b5937ee24f/doc/New_Images/screenshot_myCodes4.png)
1. This show the user's own library of codes (more code information _(already seen above)_ can be accessed by clicking a code.

![](https://github.com/CMPUT301W23T03/IHuntWithJavalins/blob/7c9864e981b8ffbeb09b00c5c9eaf3b5937ee24f/doc/New_Images/screenshot_scoreboard4.png)
![](https://github.com/CMPUT301W23T03/IHuntWithJavalins/blob/7c9864e981b8ffbeb09b00c5c9eaf3b5937ee24f/doc/New_Images/screenshot_otherUser4.png)
![](https://github.com/CMPUT301W23T03/IHuntWithJavalins/blob/ebc7f5c3fe2c2a702721d70e738f07be96a41159/doc/New_Images/screenshot_otherUsersCodes4.png)
1. This page displays scoreboard and we are also able to search different Ids <US 01.04.01, US 01.07.01, US 05.01.01>
2. We can see also see additional information of users  <US 05.01.01, US 05.02.01, US 02.03.01>
3. This page lets us browse QR codes that other players have scanned <US 02.03.01><US 02.04.01>

![](https://github.com/CMPUT301W23T03/IHuntWithJavalins/blob/ebc7f5c3fe2c2a702721d70e738f07be96a41159/doc/New_Images/screenshot_profile4.png)
1. This shows us our profile (with rankings).


# Storyboard Use-Case Sequence Diagrams
* Diagrams of storyboard sequences in application; includes transitions between different states of the user interface (caused by the user manipulating the controls or providing input). The diagrams have references of the relevant requirements and show how they are all covered.

![](https://github.com/CMPUT301W23T03/IHuntWithJavalins/blob/ebc7f5c3fe2c2a702721d70e738f07be96a41159/doc/New_Images/storyboard_signupORlogin4.png)

* The start-page (app title page) will begin when you start the app; it will navigate to a Sign-up/Login screen (if you haven't signed up nor logged in).
* The sign-up page asks for info like: username, email, password, _(we can remove this is if outside the scope of our 301 project)_ ), and your regional location (for local scoreboard comparisons against other local players) <US 04.01.01>. _(our app will disregard password for signup/login since we want to simplify the app for the project)_ 
* The login page will ask for your username, email, and password.
* Both sign-up and login prompts will navigate to the homescreen (quick navigation) _(which will be explained in the following paragraph)_.
* _For our app the sign-up and login are combined since password is removed so the both pages can function as the same (new user will say new user, but old user will say old user when signing up)_

![](https://github.com/CMPUT301W23T03/IHuntWithJavalins/blob/ebc7f5c3fe2c2a702721d70e738f07be96a41159/doc/New_Images/storyboard_autologinANDnavigator4.png)

* When opening the app again after already being logged in, the start-page will automatically transition to your homescreen (no need to login again <US 04.02.01>).
* The homescreen is your base page. It includes a summary display of your total amount of points and codes <US 01.05.01, US 01.06.01>.
* This app homescreen is the quick navigation, which includes: camera for scanning codes, my code library, visual map of nearby codes, public scoreboards, and my profile page)._(which will be described more in the next paragraph sections)_ 

![](https://github.com/CMPUT301W23T03/IHuntWithJavalins/blob/ebc7f5c3fe2c2a702721d70e738f07be96a41159/doc/New_Images/storyboard_scanCodeANDcamera4.png)

* You can begin the process to scan a code by clicking the 'scan...' button from the homescreen <US 01.01.01>; the camera will start, you can aim at the code, and the app will produce possible prompts: a success screen for catching a new code, and a redundancy confirmation screen for scanning a code you already had. 
* Successful code scans will allow you to see: what the code hash is, the generated name for it <US 02.05.01>, the points amount, the image generated for it <US 02.06.01>, the option to save the geolocation of where you captured this, and the option to take a scenic photo (of anything) for this catch <US 02.01.01, US 08.02.01>. The code page WILL NOT record what the actual code-ID actually is (for privacy/security reasons; E.g. I want to scan and score my vaccine passport, but not have my passport number recorded on there)<US 08.01.01>.
* The 'take geolocation' and 'take photo' options are setup as remembering-checkmarks so that it will remember the last given selection states for the next code scan, and still allow you to change them if you want during the next scan; Each saved code will have each of those saved options available (or not, if you selected not) when you review the code again.

![](https://github.com/CMPUT301W23T03/IHuntWithJavalins/blob/9075213ee3d0d83b3a76280219e926557a9c97b3/doc/New_Images/storyboard_moreinfoANDcomment2.png)

* You can touch the code picture/name after successfully scanning it (or click on the code name from your My Codes library _(explained in next section)_ ), to see further details of the code; This includes: generated name, picture (click name or picture to zoom in), hash code, point amount, date caught, any comments on this code (and a button to make comments) <US 02.02.01>, and a deletion button (if you want to remove this code). The buttons on the code's zoomed-in-photo page will allow you to view the geolocation and/or scenic-photo if you had their checkmarks selected during the code capture (or they would be greyed out, if you had them declined/unselected).
* The code's generated image will be setup in a way that very little (or none) of database storage will be needed for keeping these images <US 09.01.01>.

![](https://github.com/CMPUT301W23T03/IHuntWithJavalins/blob/ebc7f5c3fe2c2a702721d70e738f07be96a41159/doc/New_Images/storyboard_myCodesANDremove4.png)

* Your saved codes can be reached from the code library button <US 01.02.01>. Your code library consists of all your codes, which can be sorted by: point amount, name, and date acquired <US 01.04.01>. Clicking on a specific code line/name, will bring up that code's info page _(with the same information as mentioned in a previous storyboard photo)_ 
* The code's deletion button (if pressed) will do a secondary prompt to see if you are serious about deleting; this prevents you from accidentally deleting a code after an accidental delete button click. <US 01.03.01>

![](https://github.com/CMPUT301W23T03/IHuntWithJavalins/blob/ebc7f5c3fe2c2a702721d70e738f07be96a41159/doc/New_Images/storyboard_geolocation4.png)

* A map of nearby codes can be reached from the homescreen's 'check map...' button. The map shows your location pin and the location pins of nearby codes <US 05.02.01, US 06.01.01>. The pins could contain information like: point amount, address, picture.

![](https://github.com/CMPUT301W23T03/IHuntWithJavalins/blob/ebc7f5c3fe2c2a702721d70e738f07be96a41159/doc/New_Images/storyboard_scoreboardANDotherUsers4.png)

* The public scoreboard can be reached from the scoreboard button. He you can see the ratings of public user; this can be refined by: some locality (e.g. Edmonton, AB) or Everywhere. You can sort the scoreboard based on: total points, number of codes, highest single code value <US 07.01.01>. You can also refine the scoreboard results based on a search of a username (in the top search bar) <US 05.01.01>.
* Clicking on a username's line/name will open up their user profile <US 01.07.01>, which includes information like: date joined, their region, total points and place-rating, most codes and rating, and highest value code and rating. There is also a button to view a list of what codes this person has <US 02.03.01>, this can be sorted by: point value, name, or whether-or-not you also have these codes (the red text means you don't have it) <US 02.04.01>.

![](https://github.com/CMPUT301W23T03/IHuntWithJavalins/blob/ebc7f5c3fe2c2a702721d70e738f07be96a41159/doc/New_Images/storyboard_profile4.png)

* Your profile can be reached from the profile button. This shows you personal profile info, and your estimated scoreboard ratings and totals for categories of: total points, most codes, and highest value code <US 07.02.01>.
