<template>
  <div id="about-page" class="font-monospace">
    <NavBar/>
    <b-container class="mt-5">
      <b-row>
        <b-col/>
        <b-col cols="10">
          <div id="about-content">
            <h1>About CloudTraceBucket</h1>
            <div class="mt-5">
              <div>
                <h3 class="mt-2 pb-2 fw-bold">What is CloudTraceBucket?</h3>
                <h5>
                  CloudTraceBucket - is a web application, which allows cloud researchers to easily upload,
                  download and visualize data based on user-selected query filters.
                </h5>
              </div>
              <div class="mt-5">
                <h3 class="pb-2 fw-bold">What is Trace Data?</h3>
                <h5>
                  Trace Data - data created in online systems that show user/computer behaviours on the system.
                  It includes time of requests, latency, actions, intercommunication, and other system's information.
                </h5>
              </div>
              <div class="mt-5">
                <h3 class="pb-2 fw-bold">What are Trace Data Types?</h3>
                <h5>
                  Trace Data Type is a categorisation of the system where the trace data comes from.
                  <b>CloudTraceBucket</b> uses next cloud trace types:
                  <br>
                  <div class="mt-4">
                    <ul>
                      <li>
                        <b><i>SERVERLESS_PLATFORM</i></b> - is a cloud-native development model that allows developers to build
                        and run applications without having to manage the servers.
                      </li>
                      <li class="pt-3">
                        <b><i>CLOUD_STORAGE</i></b> - is a cloud computing model that stores data online through
                        a cloud provider (e.g. Amazon, Google, Alibaba) that manages storage as a service.
                      </li>
                      <li class="pt-3">
                        <b><i>CLOUD_CLUSTER</i></b> - is a group of nodes hosted on virtual machines
                        and connected within a virtual private cloud.
                      </li>
                    </ul>
                  </div>
                </h5>
              </div>
              <div class="mb-5">
                <h3 class="pb-2 fw-bold">Currently supported browsers</h3>
                <div>
                  <ul>
                    <li>
                      <b><i>Brave (>= Version 1.37.116)</i></b>
                    </li>
                    <li class="pt-3">
                      <b><i>Chrome (>= Version 100.0.4896.127)</i></b>
                    </li>
                    <li class="pt-3">
                      <b><i>Safari (>= Version 15.3)</i></b>
                    </li>
                  </ul>
                </div>
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
  name: 'AboutPage',
  components: { Footer, NavBar },
}
</script>

<style scoped>
  #about-content {
    min-height: 82vh;
    display: flex;
    flex-direction: column;
  }
</style>
