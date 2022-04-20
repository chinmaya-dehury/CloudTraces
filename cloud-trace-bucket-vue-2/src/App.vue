<template>
  <div id="app" class="font-monospace">
    <NavBar/>
    <b-container class="mt-5 mb-5">
      <b-row>
        <b-col/>
        <b-col cols="10">
          <div id="home-page">
            <div class="main-content">
              <h1>CloudTraceBucket:
                <span>Cloud Trace Visualization and Management Platform</span>
              </h1>
              <br>
              <div>
                <span>
                    To start first upload cloud trace data
                    <b-link :to="{ name: 'TraceFileUpload' }">here</b-link>
                </span>
              </div>
            </div>
          </div>
        </b-col>
        <b-col/>
      </b-row>
    </b-container>
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

<style scoped>
  #home-page {
    min-height: 78vh;
    display: flex;
    flex-direction: column;
  }
  .main-content {
    margin: auto;
  }

  span {
    font-size: 1.35rem;
  }
</style>
