/**
 * Sample React Native App
 * https://github.com/facebook/react-native
 *
 * @format
 */

import React, {useEffect, useState} from 'react';
import type {PropsWithChildren} from 'react';
import {
  LogLevel,
  OSNotificationPermission,
  OneSignal,
} from 'react-native-onesignal';
import {
  SafeAreaView,
  ScrollView,
  StatusBar,
  StyleSheet,
  Text,
  useColorScheme,
  View,
} from 'react-native';

import {Colors, Header} from 'react-native/Libraries/NewAppScreen';

import {appId} from './credentials.json';

type SectionProps = PropsWithChildren<{
  title: string;
}>;

function Section({children, title}: SectionProps): JSX.Element {
  const isDarkMode = useColorScheme() === 'dark';
  return (
    <View style={styles.sectionContainer}>
      <Text
        style={[
          styles.sectionTitle,
          {
            color: isDarkMode ? Colors.white : Colors.black,
          },
        ]}>
        {title}
      </Text>
      <Text
        style={[
          styles.sectionDescription,
          {
            color: isDarkMode ? Colors.light : Colors.dark,
          },
        ]}>
        {children}
      </Text>
    </View>
  );
}

OneSignal.Debug.setLogLevel(LogLevel.Verbose);
OneSignal.initialize(appId);

const useSubscription = () => {
  const [subId, setSubId] = useState('');
  const [isOptId, setOptIn] = useState(false);

  useEffect(() => {
    const initializeData = async () => {
      const permissionStatus = await OneSignal.Notifications.permissionNative();
      console.log('permissionStatus: ', permissionStatus);

      const shouldRequestPermission =
        permissionStatus === OSNotificationPermission.NotDetermined ||
        permissionStatus === OSNotificationPermission.Provisional;
      console.log('shouldRequestPermission: ', shouldRequestPermission);

      if (shouldRequestPermission) {
        await OneSignal.Notifications.requestPermission(true);
      }

      const subId = OneSignal.User.pushSubscription.getPushSubscriptionId();
      setSubId(subId);

      const isOptIn = OneSignal.User.pushSubscription.getOptedIn();
      setOptIn(isOptIn);
    };
    initializeData();
  }, []);

  return [subId, isOptId];
};

function App(): JSX.Element {
  const isDarkMode = useColorScheme() === 'dark';

  const backgroundStyle = {
    backgroundColor: isDarkMode ? Colors.darker : Colors.lighter,
  };

  const [subscriptionId, isOptIn] = useSubscription();

  return (
    <SafeAreaView style={backgroundStyle}>
      <StatusBar
        barStyle={isDarkMode ? 'light-content' : 'dark-content'}
        backgroundColor={backgroundStyle.backgroundColor}
      />
      <ScrollView
        contentInsetAdjustmentBehavior="automatic"
        style={backgroundStyle}>
        <Header />
        <View
          style={{
            backgroundColor: isDarkMode ? Colors.black : Colors.white,
          }}>
          <Section title="Subscription Id">{subscriptionId}</Section>
          <Section title="Is Opt In">{isOptIn ? 'YES' : 'NO'}</Section>
        </View>
      </ScrollView>
    </SafeAreaView>
  );
}

const styles = StyleSheet.create({
  sectionContainer: {
    marginTop: 32,
    paddingHorizontal: 24,
  },
  sectionTitle: {
    fontSize: 24,
    fontWeight: '600',
  },
  sectionDescription: {
    marginTop: 8,
    fontSize: 18,
    fontWeight: '400',
  },
  highlight: {
    fontWeight: '700',
  },
});

export default App;
