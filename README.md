# RickAndMortyApp

Having scalability as it’s main feature, the first decision was to separe the application in modules. the app was separated in application layer, where the user will interfaces with the application and its web service’s layer, that contains the REST calls to the repository used. Based on the open-closed principle, the first one depends on the second, using the rest services but making no changes on any of the module’s components.  In the same hand, preserving this layer made it possible to apply tests to make sure the requests will return as intended. 
For the app, it was used the following libraries:

# Domain

Retrofit : there is not so much to talk about Retrofit, as is one of the most famous REST libraries for android. Is tested and true to me and is the rest library that I am most comfortable to use. Also, is my go to choice since it has integration with rxJava/rxAndroid. 

rxJava : this extremely powerful library turned things a lot easier to me, making data transformation a walk in the park and providing a very elegant solution. 

glide : while Picasso is my go to choice, this time glide was a better option because of it’s option to generate thumb nails. Since the application would be basically a list, it could make the loading less resource intensive while drawing so many viewholders.

