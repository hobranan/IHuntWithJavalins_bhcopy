# February 8, 2023 - In-Lab Group Meeting
Member Attendance (Github Username)
- [x] hblow
- [x] hobranan
- [x] VirajCommits
- [x] Kshah10
- [x] TannerLH
- [x] amoghmpanhale

Discussion Notes
<ul>
<li> The deliverables, even more so for CRC and User Stories, need effort to ensure they are good and representative of the problem description.</li>
<li> Divvy up the project work into twos(more or less) and step it up to finish part 2 deliverables by the due date. </li>
</ul>

Goals for Next Meeting
<ul>
<li> Complete all deliverables by the due date in good quality. </li>
<li> Have a meet up outside of the lab to consolidate our thoughts and work on the project. </li>
</ul>



# February 11, 2023 - Out-Of-Lab Group Meeting
Member Attendance (Github Username)
- [x] hblow
- [x] hobranan
- [x] VirajCommits
- [x] Kshah10
- [x] TannerLH
- [x] amoghmpanhale

Discussion Notes
<ul>
Met up to discuss for Project Part 2. Viraj and Harrison worked on CRC cards, Khushi and Brandon worked on UI and storyboard case flow, Amogh and Tanner worked on user stories. We had both small group meetings with our partners and group meetings with all 6 of us present working together to discuss and put together the project part 2 in time.

[Update] Everyone contributed equally!
</ul>

Goals for Next Meeting
<ul>
<li> Complete all Part2 deliverables by the due date in good quality. </li>
<li> Have any Part3 work and/or questions ready for the TA for Wednesday's meeting. </li>
</ul>

# February 15, 2023 - In-Lab Group Meeting (Part2 Deliverable's Feedback)
Member Attendance (Github Username)
- [x] hblow
- [x] hobranan
- [x] VirajCommits
- [x] Kshah10
- [x] TannerLH
- [x] amoghmpanhale

Part2 Deliverable's Feedback Notes
- Need to have realistic data, test cases, and demonstrations. Write “John Doe” or a name of the sort instead of theoretical data.
- Need a way to upload an existing image or a QR code. 
- Need to add a way to show what the QR code is in the location. Additional details on the QR code. It should give some information and a way to save it. Suggestion was to add extra details about the QR code.
- Remove login from implementation. Can keep it in the storyboard but don’t implement it in the project. Doesn’t need authentication. Only needs username identification.
- Contact information needs to be there in the app.
- Should have 3 meeting minutes by now for 3 weeks, a meeting minute for every week
- Don’t need DB connection for QR code in the CRC cards. Merge the connector with the DB class.
- Searching QR code is missing. Need to add it to the CRC cards.
- TA said that we’ve done a good job on the CRC cards.
- Need to work better on organizing the team meeting notes.
- Pay attention to UI test cases. Have 2 people on the UI test cases and people on the UML.

Project Work Allocations
- Group decided to split work as UI/.xml and Backend/.java work; Half of team will work on each type.
- Brandon will work on UI/.xml stuff; Starting with title screen, sign-up page, homescreen, quick-navigation button/fragment, then work forward (in the same manner as the story flow path, e.g.).
- Harrison will work on Backend/.java stuff.

Goals for Next Meeting
- Bring a UML in the next meeting. He needs to see a UML. </li>
- Have any Part3 work and/or questions ready for reading week's meeting (if it happens during reading week).

# February 26, 2023 - Out-Of-Lab Group Meeting
Member Attendance (Github Username)
- [x] hblow
- [x] hobranan
- [x] VirajCommits
- [x] Kshah10
- [x] TannerLH
- [x] amoghmpanhale

Questions Discussed on our Design
- Should Comment be just a string or another model class? How to store in database(subcollection of a document)?
- What should the visual representation of the QR Code be? If we use AI for example how many images should we have prepared?
- General format of the database, should we have Collection of Users who have Codes or something else?
- Are comments local or universal(ie. If 2 people, John and Sam, have scanned the same QR code. If I comment on John's, is the comment local to John's scan or universal to both people).
- How do we include maps SDK, since getting an API key requires an account with billing information?

Project Work Allocations
- Refer to the user story allocation in the project board, there may be new assignees to user stories as weekly meet ups continue(as some do visuals and others work on backend)

Goals for Next Meeting
- Make a database that links to some activity in the application(should be viewable by all members)
- Finish the XML of the application
- Finish starter functionality(ie. activities, classes, etc)

# March 1, 2023 - In Lab Meeting
Member Attendance (Github Username)
- [x] hblow
- [x] hobranan
- [x] VirajCommits
- [x] Kshah10
- [x] TannerLH
- [ ] amoghmpanhale

TA Tips:
- Use constrained layout for XML
- Integrate the user stories(prioritize), it's tough
- if using ai generated images, need at minimum 20 for a decent demo
- Add links to the UML next time, those are important(where a lot of the errors are prone)
- Activities on top, controllers and database in middle, model on bottom(industry standard, don't have to follow but it would be good practice)
- Bundles may be useful(just one possible implementation)
- OpenStreetMap instead of google maps(open maps will be harder to use however, though it's open source and no need to worry about subscriptions)
- If implementation not explicitly described by project description, implementation up to us(QR Code comments for example may be local or universal, up to us)

Goals for Next Meeting:
- Work on integrating user stories into application(since next week is the last meeting with TA get a decent amoutn done)
- Finish the UML diagram

# March 7, 2023 - Out-of-Lab Group Meeting
Member Attendance (Github Username)
- [x] hblow
- [x] hobranan
- [x] VirajCommits
- [x] Kshah10
- [x] TannerLH
- [x] amoghmpanhale

What you did:
- Brandon: 
   - worked on main project to create functional activities just to get the project functionality started and have some root project to go off of.
   - added maps functionality (OpenStreetMap) that can place points on map with info (name, points) and grab the phone's current lat/long (US: 5.02, 6.01).
   - added camera functionality to app to capture code, use sha-256 conversion, extract name/points/hash into QRCode object, and display that on the app (US: 1.01, 2.01, 2.05, 8.01).
   - added functionality to connect to firebase and (from lab5 template) update a listview that connects/updates to/from firebase and can added new qr codes (via camera).
- Khushi:
   - worked on updating UML.
- Amogh:
   - worked on updating UML.
- Harrison:
   - created firebase for project.
- Viraj:
   - worked on code deletion functionality (button deletion) within viewing MyLibrary's specific codes, and attached that to firebase (US: 1.03).

What you are going to do (and any goals set for next meeting):
- Brandon: 
   - trying to improve maps to grab other user's nearby codes  (US: 5.02, 6.01).
   - trying to implement pictures to attach to a QRCode (along with Firebase Storage) (US: 2.06).

Any obstacles in your way?:
- Maps is OpenStreetMap which has very little documentation; may have to switch to Google Maps later.
- Hard to pinpoint specific obstacles. Project is still in infancy.

# March 8, 2023 - In-Lab Group Meeting with TA
Member Attendance (Github Username)
- [x] hblow
- [x] hobranan
- [x] VirajCommits
- [x] Kshah10
- [x] TannerLH
- [x] amoghmpanhale

TA Notes:
- Upcoming labs we will be meeting at 5pm, if professor decides in person, we will meet in person, otherwise TA will ask us for a vote on meeting online or in person.
- Have to keep committing when you are coding(Important) as we are pretending to be working in a professional environment
- Don't push on the last day, push throughout the week
- For the UML, make sure you do not have more than 4 classes in a row(affects readability)
- Divide UML into 3 layers, activities fragments UI stuff top layer, controllers middle layer, model classes bottom layer(Mentioned last meeting, good for organization)
- UML identify types(ie. Inheritence, generalization, specialization, multiplicities, etc.)
- Careful on composition, if connected by composition they have to be linked with one another ie. if one is lost, the other is also lost.
- Whenever you have a one to many relationship, that representings a list(UML). Try not to be redundant as it already conveys the information graphically
- Test cases important(Automated only for model classes as those are getters and setters)
- Must use github actions for this project as well, UI testing also is important.
- Use RelativeLayout and/or 'padding' property to get xmls to fit on most screens
- We need weekly minutes on the wiki(Important), if throughout the week keep notes on it in the wiki

Goals for Next Meeting:
- Keep in sync with what everyone has been completing and get that work done
- Better communication between team members throughout our creation process
- Testing is very important so get to writing! Note we all should contribute to writing the testing if I remember.
- Demoing project part 3 next week so be ready

Needed for Monday(Project Part 3):
- UML 100% complete
- Test cases and functionality needed for that 50% user stories completed
- The project part 3 description holds info on other requirements needed

# March 11, 2023 - Out-of-Lab Group Meeting
Member Attendance (Github Username)
- [x] hblow
- [x] hobranan
- [x] VirajCommits
- [x] Kshah10
- [x] TannerLH
- [x] amoghmpanhale

What you did:
- Brandon:
   - Added title page (with example photos) and combined signup/signin activity to title and quick-nav (remembers user from shared preferences); combined quick-nav and homescreen (since it's redundant to keep separate); added photo checkbox and geolocation checkbox during code capture (photo is just static copy; photo capture still needs to be implemented); added photo view and geolocation view to codeview; combined viraj's delete popup-builder to the delete code functionality. (US: 1.02, 1.03, 4.01, 4.02, 8.02).

- Khushi:
  - Worked on XML files, UML, US I was assigned to

- Harrison:
  - Created Player model class, PlayerDB QRCodeDB and DBConnection classes for firestore database accesses including functionality for retrieving player codes and sorting based on field in documents. Mainly worked on database design. Added testing for QRCode and Player model classes and part of PlayerDB testing. (US: 1.02.01, 04.02.01, 01.03.01, 01.01.01)

- Amogh:
  - Added javadocs to existing project code. Worked with Khushi to design the UML diagram for an overview of the project. Worked on XML files and User stories I was assigned to.   


What you are going to do (and any goals set for next meeting):
- Brandon:
   - Satisfy part3 deliverables: complete feedback from TA; Add more app functionality to satisfy at least 12/23 US (probably US: codes within geoloc 5.02, profile with total points and total codes 4.01, 1.05, 1.06); javadocs; ui test cases and github actions; update UI mockups.
- Khushi: 
   - Help satisfy part3 deliverables
- Harrison:
   - Complete testing for PlayerDB and CodeDB then create CommentDB functionality for comment access in the database. 
- Amogh:
   - Help satisfy part 3 deliverables.
   - Help implement MVC structure into project code.
   - Complete junit and UI testing for Camera, Map, Leaderboard.
   - Help update User Interface and functions for smoother performance.

Any obstacles in your way?:
- Brandon:
   - the camera functionality seems to have a hard time switching from catching/scanning to caught 'new' vs 'already' caught (the camera seems to scan a code like 4 times; need to adjust code to only register once so it think it didnt already catch it when it actually did catch new codes); the map seems to refresh constantly which makes the back-button functionality frozen, some setting is needed in OSMaps to lower the refreshing rate or something); need to better setup local app data remembrance so that data transfer between activities can be simplified (and it better helps the MVC model).
- Khushi: 
   - need to adjust
- Harrison:
   - Better team communication to prevent us from doing the same parts of the project(happened twice for part 3). Understanding how to use lambda's better also would help as I am basing most of the database functionality off Well Fed project's functionality which uses lambdas. I also should set aside more time(or better manage it) to work on the project more consistently compared to part 3.
- Amogh:
   - Need for better team communication as 3 of us worked on the same part of the project which slowed down progress significantly. I should also reevaluate my usage of version control to avoid incompatibility between branches. 

Needed for Monday(Project Part 3):
- The project part 3 description holds info on other requirements needed: 
see project board or here: https://eclass.srv.ualberta.ca/mod/assign/view.php?id=6767424


# Sprint Planning
Member Attendance (Github Username)
- [x] hblow
- [x] hobranan
- [x] VirajCommits
- [x] Kshah10
- [x] TannerLH
- [x] amoghmpanhale

Feb 26th Week:
- Set a database that links to some activity in the application(should be viewable by all members)
- Work and got done XML files on US that were assigned to each of us
- Finish starter functionality(ie. activities, classes, etc)
 
- Harrison: US 01.01.01, US 01.03.01
- Brandon: US 01.01.01, US 01.06.01, US 02.02.01, US 02.01.01
- Viraj: US 01.03.01, US 01.06.01
- Khushi: US 01.01.01, US 02.06.01
- Tanner: US 02.06.01, US 02.02.01
- Amogh: US 02.01.01, US 02.06.01

March 6th Week:
- trying to improve maps to grab other user's nearby codes  (US: 5.02, 6.01)
- trying to implement pictures to attach to a QRCode (along with Firebase Storage) (US: 2.06)
- UML 100% complete
- Test cases and functionality needed for that 50% user stories completed

- Harrison: US 01.02.01, 05.01.01, 05.02.01
- Brandon: US 04.01.01, US 04.02.01, US 06.01.01, US 05.02.01, US 08.01.01, US 02.05.01
- Viraj: US 02.04.01, US 07.01.01, US 05.01.01
- Khushi: US 01.04.01, US 07.02.01, US 02.04.01, US 01.07.01
- Tanner: US 04.01.01, US 04.02.01, US 02.04.01, US 02.05.01
- Amogh: US 04.01.01, US 04.02.01, US 06.01.01

# March 15, 2023 - In-Lab Group Meeting with TA
Member Attendance (Github Username)
- [x] hblow
- [x] hobranan
- [x] VirajCommits
- [x] Kshah10
- [x] TannerLH
- [x] amoghmpanhale

TA Notes:
- TA will ask us for a vote on meeting online or in person.
- It's important to have test cases.
- Ta will help us figure out github actions issues (on Android CI yml)

Goals for Next Meeting:
- Set up leagues for rankings (like: gold, silver, bronze, initiate)
- We can try to create code images using similar style as github user icons (Identicons); could help satisfy US 9.01, in addition to image one

# March 19, 2023 - Out-of-Lab Group Meeting
Member Attendance (Github Username)
- [x] hblow
- [x] hobranan
- [x] VirajCommits
- [x] Kshah10
- [x] TannerLH
- [x] amoghmpanhale

What you did:
- Brandon:
   - Updated scoreboard activity to use firebase (and added more users with codes to database to create a large enough scoreboard example)
- Harrison:
   - Did not work on the project this week.

What you are going to do (and any goals set for next meeting):
- Brandon:
   - same as before: Satisfy part4 deliverables: Add more app functionality to satisfy all US (probably US: codes within geoloc 5.02, profile with total points and total codes 4.01, 1.05, 1.06); github actions;
- Harrison: 
   - Same as last meeting, finish testing classes and reviewing DB classes(adding commentDB if not already there)

Any obstacles in your way?:
- Brandon:
   - same as before: the camera functionality seems to have a hard time switching from catching/scanning to caught 'new' vs 'already' caught (the camera seems to scan a code like 4 times; need to adjust code to only register once so it think it didnt already catch it when it actually did catch new codes); the map seems to refresh constantly which makes the back-button functionality frozen, some setting is needed in OSMaps to lower the refreshing rate or something); need to better setup local app data remembrance so that data transfer between activities can be simplified (and it better helps the MVC model).
- Harrison:
   - Same as last meeting except in the immediete future there is a midterm I have to study for on monday.

# March 22, 2023 - In-Lab Group Meeting
Member Attendance (Github Username)
- [x] hblow
- [x] hobranan
- [x] VirajCommits
- [x] Kshah10
- [ ] TannerLH
- [x] amoghmpanhale

TA Tips:
- Keep meeting minutes ongoing
- After submission Wednesday same time presentation, 4 minutes(test the computer on the projector), in person, live demo
- Demo test data should be realistic
- Keep a video backup of the demo in case
- Test cases do not need to be run for the demo
- Demo is "selling the app" to the customer(walk them through all important user scenarios)
- Prof will ask technical questions at the end
- Could have 2 emulators on the screen to show how users interact with eachother or use an actual phone and project onto the emulator

Next Meeting Tips:
- Try to have everything done by next meeting!

# March 22, 2023 - Out-of-Lab Group Meeting
Member Attendance (Github Username)
- [x] hblow
- [x] hobranan
- [x] VirajCommits
- [x] Kshah10
- [x] TannerLH
- [x] amoghmpanhale

What you did:

We discussed user stories that were done and user stories that needed to be finished before the next meeting. 
We divided up the work equally
Firstly, all user stories will be worked on, keeping in mind the MVC format
Secondly, tests will be worked on + creating Javadocs for all written code
Thirdly, Clean up of any code, UI visual, preparing for presentation

# March 26, 2023 - Out-of-Lab Group Meeting
Member Attendance (Github Username)
- [x] hblow
- [x] hobranan
- [x] VirajCommits
- [x] Kshah10
- [x] TannerLH
- [x] amoghmpanhale

What you did:
- Brandon:
   - Added photo grabbing functionality; added to maps the nearby codes (still need to filter out ones i own); updated xmls to have black background with gold letters and lots of centering layouts formatting; added comments functionality
- Viraj:
   - Added other user profile viewing from scoreboard; added ranking algorithms from other user's profile page
- Harrison:
   - Completed DB class testing and started converting activity functionality to use controllers where applicable

What you are going to do (and any goals set for next meeting):
- Brandon:
   - see deliverables below
- Harrison:
   - complete the conversion to using controller classes and test to make sure the functionality remains consistent before and after the change

Any obstacles in your way?:
- Brandon:
   - just finishing the app in time
- Harrison:
   - same as Brandon, the time crunch is hitting which should have been solved through better time management and planning earlier on.

Deliverables::
1. Feedback: 
- Set up leagues for rankings (like: gold, silver, bronze, initiate)
- We can try to create code images using similar style as github user icons (Identicons); could help satisfy US 9.01, in addition to image one
2. Code Base:
- Set up code to be modular (low coupling, high cohesion); good practice of saving of model data in certain activities; references/citations of other code
3. Code comments and Javadocs:
- For each source file, you should have a brief introductory comment describing its purpose or role within the application or a design pattern, as well as any currently outstanding issues. Provide Javadoc interface documentation for your model classes and their public methods (at least).
4. Test Cases (Unit and UI)
- Write runnable tests for your model (Unit) and control classes (UI). Provide intent tests for the requirements you have done (UI). Deliver the test code to your source repository. If you have test data files, also include those. Test data should be realistic.
5. UML diagram (using the MVC flavour from #2 Code Base)
- Do this after code base is finalized (since this will reflect the code base) !
- Update your object-oriented design using a UML class diagram (or diagrams), including details on key attributes and methods. Add notes as appropriate to clarify. Include notes on the use of design patterns among the classes.
6. US-Story Product Backlog
- Update the requirements as appropriate. All user stories are done at this checkpoint.
7. User Interface Mockups and Storyboard Sequences
- Do this after app is finalized (since this will reflect the final xml layouts) !
- Update these diagrams as appropriate.
8. Sprint (Out-of-lab) Meetings
- Maintain a record of weekly work (did what, doing next, obstacles)
9. Demo for TA and teacher
- Do this after app is finalized (since this needs video copies of demo in case of android studio malfunction) !
- Plan the 4 minute demo story!
- Present an engaging demo. The final prototype should show the usability of its user interface and the degree to which its functionality fulfills the user's needs. All team members must contribute to the demo. This demo should have a logical flow in what is presented (following a story about what a user would naturally do, not just enumerating assorted features).
10. Github use
- Regular and consistent use of GitHub by all team members to share files for the project deliverables, to effectively track issues, and to manage tasks. Use GitHub. Your team repo must be self-contained, i.e., not link to external content that might change.

# March 29, 2023 - In-Lab Group Meeting
Member Attendance (Github Username)
- [x] hblow
- [x] hobranan
- [x] VirajCommits
- [x] Kshah10
- [x] TannerLH
- [x] amoghmpanhale

TA Comments:
- Unit tests are without databases. He said they are dumb test cases with stuff like getters and setters. The thing about calculating qrcode score, testing the algorithm for that would be a unit test since the database is not involved the UI is not involved. Basically testing the structural business rules before connecting to the database those are unit tests.
- Intent tests has to represent a user story. For part 3 there were 2 or 3 test cases and they were super long, don’t do that. There should be 1 test case for 1 user story. For example, saving the location with the QRCode.
- Stub methods - try to bring your software in an environment so you can test it. Use them.
- If you’re testing that you could save a location for a QRCode, you have to have a QRCode. That is a stub method.
- There will be a lot of hard coding.
- GitHub actions don’t matter as much don’t worry about it.
- The estimation part is very important. The ranking estimation percentile thing. Not just what tank they are but also the percentile.
- Do highlight it that this how we estimate it they will be interested in it
- Use the word estimation so they know that you did estimation
Tips for the demo:
- Have to rehearse. If you don’t rehearse it will be very disorganized. At least decide the user stories to present first or at least decide the order of the user stories. Ideally everyone should present. It should be interactive. Ask questions to the audience. Some people add humor to engage the audience but be careful since it can become offensive. It will be in the lab.
- People prepare a skit usually but that’s up to. Have some slides in your hand so you can say that this is the name of your project and these are the team members. Give them an idea of what the app is don’t just jump into the demo. Max 1 slide that says what the app is and then start demoing.
- The questions are usually about what object oriented principles you followed, what was hardest about the project, prof can point anyone and ask what contributions to the project were.
- There are a couple of design patterns. Say we follow TDD practice. Test Driven Development. There are design patterns for example singleton, observer, composite.
- We have lots of observer pattern, adapter pattern
- Android framework doesn’t allow you to nicely follow MVC but do your best
- Will be asked questions about general knowledge about object oriented programming. Might ask about validation. Sometimes Dr. Hindle is in the mood and he might ask you add this many digits or this many users in your demo.
- Extra user stories don’t present until the end. Present the most important user stories first.

# April 2, 2023 - Out-of-Lab Group Meeting
Member Attendance (Github Username)
- [x] hblow
- [x] hobranan
- [x] VirajCommits
- [x] Kshah10
- [x] TannerLH
- [x] amoghmpanhale

What you did:
- Brandon:
   - Updated camera scanning alg (it was grabbing wrong ones sometimes); added ranking alg to Profile Activity; refined Scoreboard activity to show customized numbering depending on actual rank position (instead of arbitrary position); region button works with other button, but search will only find players and region button will reset search (to simplify hierarchy of positions); highest code button added; updated show 'other user profile' (ShowUserProfile) to be more like the 'my profile' (ProfileActivity) since shown information is nearly identical; updated xmls (new caught and others); added regional section for map (to better satisfy US5.02); added nicer date formatting to some activities; kept MyLibrary to use firebase instead of intent-extras (to grab data) since code deletion works better with firebase-method; changed title page and app icons and images
- Harrison:
   - Completed transferring functionality from activities to controllers where applicable, helped update the uml.
- Khushi:
    - Helped with the UI testing, formatted the app, went through all different User stories to make sure all were done, Updated UML, worked with Tanner to further write the intent tests. Help make the PowerPoint presentation for the presentation itself. Fix any last min bugs and errors
- Amogh:
   - Implemented unique hashcode image visualization; added design patterns to code comments for ease of understanding; added javadoc generation capability for code; wrote unit tests for various non-app and non-database related methods; worked with Viraj to write further unit tests; provided aid with intent testing wherever possible; identified bugs in app and suggested methods to fix them. 

What you are going to do (and any goals set for next meeting):
- Brandon:
   - complete the app US and deliverables! (see deliverables in Meeting Log March 26) (basically go through each US one by one and test its validity, and do the same for Deliverables); try some Materials UI on our app; for Demo prep: need to test projector with laptop (and magnifier with phone); need to make demo videos
- Harrison:
   - practice for the upcoming demo!
- Khushi:
    - Make sure the UML is 100% complete, make sure all tests run, make sure all the user stories are complete and change any small functionality if needed. work with team to plan out a good PTT presentation 
- Amogh:
   - Prepare for demo; clean bugs with final submission; review project in order to meet requirements before the deadline; complete class comments for javadoc generation in accordance with bug fixes; work with the team to plan out a good presentation after the submission.
- Viraj:
    - Worked on Unit Tests for ScoreBoard modal class(and the subclasses).Collaborated with Amogh to write the rest of the unit tests


Any obstacles in your way?:
- Brandon:
   - just finishing the app and deliverables in time
- Harrison:
   - just finishing the app and deliverables in time
- Khushi:
   - Intent testing had a few errors but was able to finish them, didn't know which tests to write
- Amogh:
   - I was not sure how to identify unit tests at first; I also faced a few local issues with my system while running them that involved my imports; Adapting the MonsterID from the owners' php implementation was also difficult.

- Viraj
   - Looking in how to write unit tests.But was able to figure out stuff.Made Player objects and added QRCodes and worked on from there.Then it was easy

# Sprint Planning
Member Attendance (Github Username)
- [x] hblow
- [x] hobranan
- [x] VirajCommits
- [x] Kshah10
- [x] TannerLH
- [x] amoghmpanhale

March 27th Week:
- Finishing up controller classes, Intent testing, Unit testing, Comment class, and adding any small functionalities as needed
- Finalize our UI  
- Finishing up with any of the user stories we have left
 
- Harrison: US 02.02.01, US 09.01.01 
- Brandon: US 05.02.01, US 02.03.01, US 08.02.01, US 08.01.01
- Viraj: US 02.06.01, US 05.01.01, US 07.02.01
- Khushi: US 02.05.01, US 02.03.01, US 06.01.01
- Tanner: US 01.07.01, US 02.04.01, US 02.06.01
- Amogh: US 01.04.01, US 01.05.01, US 02.02.01


