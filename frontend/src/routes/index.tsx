import { createFileRoute } from '@tanstack/react-router'
import { SignedIn, SignedOut, SignIn,SignInButton,UserButton,useUser  } from '@clerk/clerk-react'

export const Route = createFileRoute('/')({
  component: App,
})

function App() {
  const { isSignedIn, user, isLoaded } = useUser()

  if (!isSignedIn) {
    return <SignIn />
  }

  return <div>Welcome! {user.firstName} 
  <div> 
     <SignedIn>
        <UserButton />
      </SignedIn>
      <SignedOut>
        <SignInButton />
      </SignedOut></div>
   </div>
}
