<template>
  <div id="download-files">
    <NavBar/>
    <b-container class="mt-5">
      <b-row>
        <b-col/>
        <b-col cols="10">
          <h1>Download Trace Files</h1>
          <div class="file-table">
            <b-table striped hover :items="files"></b-table>
          </div>
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
import { getAllTraceFiles } from "@/apiClient/storageApi";

export default {
  name: "DownloadFiles",
  components: { Footer, NavBar },
  data() {
    return {
      files: [],
    }
  },
  created() {
    const fetchFiles = async () => {
      this.files = await getAllTraceFiles();
    };

    fetchFiles();
  },
}
</script>

<style scoped>

</style>
