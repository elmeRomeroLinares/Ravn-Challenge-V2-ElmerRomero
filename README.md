# Ravn-Challenge-V2-ElmerRomero
Ravn code challenge uses Star Wars GraphQL Api to display characters profiles. 

Hi! To install Ravn V2 app in your Android device, please follow the steps below.  
From Android Studio, in an Android device with developer options enable. 
1.	Clone repository to your local machine from this link. For this process use ‘git clone’ on a command prompt. 
2.  After Android Studio ends building the proyect, run the app in an Android mobile device or Android virtual device.  
 	 
Project Description: Ravn Challenge V2 is an Android App that allows you to know more about Star Wars characters. You can get more information about your favorite characters by tapping on a character in the list shown in the fist screen. If an error occurs while loading the list of characters, you would see an error message indicating that something went wrong. You would see the list updating five characters at the time. If you tap a character in the list, you would see a new screen showing you general attributes of the character and also the vehicles they used in the movie. If something goes wrong, you would see the corresponding error message.  If you tap the upper arrow, you would be redirected to the list of characters.      

Here are some screenshots of the working app:
![FailedToLoadListOfCharacters](https://user-images.githubusercontent.com/52327494/110285700-417f6280-7fa9-11eb-9408-f1e8b6d28ce1.jpg)
![FailedToLoadCharacterAttributes](https://user-images.githubusercontent.com/52327494/110285704-43492600-7fa9-11eb-96a4-732b49e95ab9.jpg)
![ListOfCharactersLoadedFromServer](https://user-images.githubusercontent.com/52327494/110285709-4512e980-7fa9-11eb-86ac-a5f535fe9551.jpg)
![LoadingViewOnCharacterList](https://user-images.githubusercontent.com/52327494/110285714-46dcad00-7fa9-11eb-9ee6-7d67cffa5fb8.jpg)

Assumtions: 
"People of Star Wars" text on toolbar changes to "People", when all tha characters available in allPeople method in the server are loaded.
This app doesn't persist data, causing a new server request every time a fragment is left behind by the navigation of the user. 
After a failure in receiving the list of characters has occurred, a buttom or swipe down event should be implemented to trigger a new request to the server.

Technologies Used:
This app was developed using the guideline provided by appollo documentation for android implementation. The use of coroutines is in accordance to this documentation https://www.apollographql.com/docs/android/tutorial/01-configure-project/. This app has been developed ussing androidx navigation components and data binding.  
