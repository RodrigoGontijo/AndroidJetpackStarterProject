This is a project based on this Udemy course ( https://www.udemy.com/course/androidjetpack/ ). 
I've changed/added some new things to improve the code, like :
- Kotlin Coroutines
- Dependency Injection with Koin ( i've modularized the code )
- Unit tests ( TO DO )
- Method calls
- The organization of the files

Fixed one bug i've found on the project also: 
Every time the Fragments was created, it created a new observable of the ViewModel's livedata without cancelling the previous ones, so i've Override the onDestroyView() and remove the observables 
