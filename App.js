import React from "react";
import { View, Text, StyleSheet } from "react-native";

if (typeof global.setImmediate === "undefined") {
  global.setImmediate = (callback, ...args) => {
    return setTimeout(callback, 0, ...args);
  };
}

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
