<template>
  <div id="download-files">
    <NavBar/>
    <b-container class="mt-5">
      <b-row>
        <b-col/>
        <b-col cols="10">
          <h1>Download Trace Files</h1>
        </b-col>
        <b-col/>
        <Footer/>
      </b-row>
    </b-container>
  </div>
</template>

<script>
import NavBar from '@/components/Navbar';
import Footer from '@/components/Footer';

export default {
  name: "DownloadFiles",
  components: { Footer, NavBar }
}
</script>

<style scoped>


</style>