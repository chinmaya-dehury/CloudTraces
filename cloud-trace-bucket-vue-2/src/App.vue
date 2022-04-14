<template>
  <div id="app">
    <NavBar/>
  </div>
</template>

<script>

import NavBar from "@/components/Navbar";
export default {
  name: 'App',
  components: {
    NavBar
  }
}
</script>

<style>
#app {
  width: 100%;
}
</style>
