import React from "react";
import { View, Text, StyleSheet } from "react-native";
import "react-native-polyfill-globals/auto";

export default function App() {
  return (
    <View style={styles.container}>
      <Text>Welcome to the Fitness App!</Text>
    </View>
  );
}

const styles = StyleSheet.create({
  container: {
    flex: 1,
    justifyContent: "center",
    alignItems: "center",
  },
});
