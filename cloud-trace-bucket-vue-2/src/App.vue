<template>
  <div id="app">
    <NavBar/>
    <Footer/>
  </div>
</template>

<script>

import NavBar from '@/components/Navbar';
import Footer from '@/components/Footer';

export default {
  name: 'App',
  components: {
    Footer,
    NavBar
  }
}
</script>

<style>

#app {
  width: 100%;
}

</style>
