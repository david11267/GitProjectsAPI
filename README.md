# Git Projects API

## About 
This is a mono repo for a spring boot api and a sveltekit frontend dasshboard controlling and observing the api.

## How it works
It works by inspecting your github repositories, excluding the ones from your blacklist (found in api dashboard options)
then passing the raw github data to an Ai model of your choice (selection found in api dashboard options)
and finally the api responds with a list of project objects you can map out on your website or otherwirse. 


<img width="524" height="418" alt="image" src="https://github.com/user-attachments/assets/50b4d1d8-429b-4409-befd-2f92f63bb47a" />


## Quick start
1. Register your account at: https://git-projects-api.vercel.app/
2. Set your options
3. Make a request: at : curl 'https://spring-backend-a4qk4.ondigitalocean.app/api/projects'
