# QRCode
## QRCode
|Responsibilities|Collaborators|
|----------------|-------------|
|Keep a relatively unique visual representation of QRCode| Player|
|Keep a human readable name representation of QRCode| |
|Keep a photo of QRCode item/location(Optional)| |
|Keep geolocation of QRCode based on User GPS location(Optional)| |
|Keep calculated score of QRCode| |
|Keep comments on QRCode from Players| |
## QRCodeActivity
|Responsibilities|Collaborators|
|----------------|-------------|
|Displays the data in QRCode from QRCodeList from QRCodeController|QRCodeController|
|Displays a button for deleting a QRCode from the Player(on click brings up a fragment for deletion validation). PlayerController handles deletion of QRCode from Player and if no Player has QRCode, QRCodeController handles deletion from database|PlayerController|
|Displays a button for commenting on a QRCode(on click brings up a fragment for comment addition and confirmation). QRCodeController handles the addition|Code|
|Displays button to search for a QRCode|QRCodeDB|
| |Player|
## QRCodeController
|Responsibilities|Collaborators|
|----------------|-------------|
|Stores a QRCodeList on each QRCode in QRCodeDB|QRCodeDB|
|Add/Delete QRCode item from QRCodeDB and updates QRCodeList and QRCodeActivity accordingly|QRCode|
|Adds comments from Player to QRCode item in QRCodeDB and updates QRCodeList and QRCodeActivity accordingly|QRCodeActivity|
|Searches the QRCode entered by the user in the firestorm database and returns the output, if its present|QRCodeDB|
## QRCodeDB
|Responsibilities|Collaborators|
|----------------|-------------|
|Edit/Delete a QRCode from the Firestore Database|QRCode|
|Get unsorted/sorted QRCode data from the Firestore Database|QRCodeActivity|
|Adds a QRCode to the Firestore Database||
|Connects the application to the Firestore Database||
## QRCodeMapActivity
|Responsibilities|Collaborators|
|----------------|-------------|
|Display local map based on Player geolocation from GPS location|QRCode|
|Display QRCodes data from QRCodeList on local map based on geolocation|Player|
# Player
## Player
|Responsibilities|Collaborators|
|----------------|-------------|
|Keeps unique username of Player| QRCode|
|Keeps Player contact information| |
|Keeps Player region information| |
|Keeps list of QRCodes(PlayerQRCodeList) Player has scanned| |
|Keeps track of Player's highest/lowest scoring QRCode, sum of scores of QRCodes, and amount of QRCodes| |
|Keeps relative track of Player ranking in region| |
## PlayerActivity
|Responsibilities|Collaborators|
|----------------|-------------|
|Displays Player information from PlayerList from PlayerController|Player|
|Displays a button to bring up Player's scanned QRCodes, on click brings up PlayerQRCodeBrowserActivity to view owned QRCodes|PlayerController|
||PlayerQRCodeBrowserActivity|
## PlayerBrowserActivity
|Responsibilities|Collaborators|
|----------------|-------------|
|Display profiles on Player from PlayerList from PlayerController|Player|
|Brings up PlayerActivity when clicking on Player|PlayerActivity|
|User can search for players based on Username|PlayerController|
## PlayerQRCodeBrowserActivity
|Responsibilities|Collaborators|
|----------------|-------------|
|Display owned QRCode from PlayerQRCodeList from Player|Player|
|Allows user to sort by highest/lowest scoring, sort by QRCodes both Player and User own, and by date|QRCodeActivity|
|Display if Player has scanned the same QRCode as user|PlayerController|
|Brings up QRCodeActivity when clicking on QRCode||
## PlayerController
|Responsibilities|Collaborators|
|----------------|-------------|
|Store a PlayerList made up of all Players from PlayerDB|PlayerDB|
|Adds/Deletes a Player from PlayerDB and updates PlayerList, PlayerBrowserActivity and PlayerActivity accordingly|PlayerActivity|
|Edits a Player from PlayerDB and updates PlayerList, PlayerBrowserActivity, and PlayerActivity accordingly|PlayerBrowserActivity|
|Searches for Player in PlayerList based on Username|Player|
|Sorts QRCodes in PlayerQRCodeList based on highest/lowest scoring, sum of scores, and total scanned for Player|PlayerQRCodeBrowserActivity|
|Finds estimate of Player ranking regionally| |
## PlayerDB
|Responsibilities|Collaborators|
|----------------|-------------|
|Adds a Player to the Firestore Database|PlayerDBConnector|
|Edit/Delete Player from the Firestore Database|Player|
|Get unsorted/sorted list of Players from Firestore Database|PlayerController|
## PlayerDBConnector
|Responsibilities|Collaborators|
|----------------|-------------|
|Connects the application to the Firestore Database|PlayerDB|






