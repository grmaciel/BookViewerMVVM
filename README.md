# BookViewer Code Overview

## Presentation

I've decided to go with MVVM + LiveData, it's not something i've used before but i thought this small example
would be a good place to play with it.

## Domain

UseCase, actually in this example it doesn't even makes sense since there's no business rule in there,
the repository could be used straight up.

## Persistence

My last apps had minimum persistence, so i hadn't use a real db in a while and decide to go with Room. After
doing some online research, seemed pretty simple to get a basic setup.

## Synchronism

Given the time constraint i did something pretty simple but unfortunately that doesn't give a polished UX experience, but this
was a trade off i made. The Remote repository will emit 2 types of data given on a random number % 2 == 0 and
when theres data locally, the order goes like this:
```
Query local -> Deliver to User -> Query Remote -> Persist Local -> Delivery to User
```

Alternatively to this could be used a JobScheduler that would sync the data in the backend for when the user
opens the app the data is already there. I think that's how your app works.

## Dependency Injection

I went with Dagger 2, not necessarily needed for such a small project but since i had setup from a previous project
i went with it.

## Tests

Given the time constraints i focused the tests on the Repository and the Mappers, the ViewModel i had some problems
to test it regarding the LiveData, so i gave up otherwise would take even more time. Given the time too i didn't do
any UI test with Espresso, i've done before for custom ui components.

## UI/UX

Unfortunately i didn't had time to polish the experience with animations, better design or a better synchronism
to help on that, so i feel this is the weaker part of my project and that's unfornate since i'm used to create UI
components and animations.

## Changes

If i would spend more time on it, the first things i would tackle would behow the synchronism work (would try a JobScheduler approach i think), 
improve test coverage and improve the UI.
