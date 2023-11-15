## Step 1: Install packages &  Start the Metro Server

```bash
# build + start
yarn restart

# OR just start, when packages are already installed
yarn start
```

## Step 2: Start your Application

Let Metro Bundler run in its _own_ terminal. Open a _new_ terminal from the _root_ of your React Native project. Run the following command to start your _Android_ or _iOS_ app:

```bash
yarn android
```

## Step 3: Updated appId

* Create `./credentials.json` file with `appId` in the project root
* or alternatively at `./App.tsx` you can remove `import {appId} from './credentials.json';` (line 27) and directly update `appId` at `OneSignal.initialize(appId);` (line 60)

## Step 4: Reproduce the issue
1. Launch the app and retrieve `subscriptionId` from the `console.log`, or from the screen
2. `Close` the App and `lock` the screen
3. `Send Push Notification` from your OneSignal App
   1. Send one more if nothing will be shown, because sometimes it does not show if the process is not started
   2. `CallUIActivity` will be shown on your lock screen
4. `Decline` the "call" -> 
   1. `finish` inside `CallUIActivity` will be called
   2. Incoming Call Activity will disappear
5. Unlock the Phone and check `collapsed` apps
   1. `CallUIActivity` will be shown there and it's impossible to `finish` it (try to open and press `Decline` again)

---

### Repro of the issue

<video src="video-repro.mp4" width="350" controls title="Title"></video>