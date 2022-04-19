<template>
  <div id="download-files">
    <NavBar/>
    <b-container class="mt-5 mb-5">
      <b-row>
        <b-col/>
        <b-col cols="10">
          <div id="files-download">
            <h1 class="mb-5">Download Trace Files</h1>
            <div class="file-table" v-if="files.length">
              <b-table
                  label-sort-asc=""
                  label-sort-desc=""
                  label-sort-clear=""
                  striped hover
                  :items="files"
                  :fields="fields"
              />
            </div>
            <div v-else>
              <h3 class="d-flex my-5">No files were uploaded yet!</h3>
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
import { getAllTraceFiles } from '@/apiClient/storageApi';

export default {
  name: "DownloadFiles",
  components: { Footer, NavBar },
  data() {
    return {
      fields: [
        { key: 'fileName', sortable: true },
        { key: 'fileFormat', sortable: true },
        { key: 'provider', sortable: true },
        { key: 'fileSize', sortable: true },
        { key: 'uploadTime', sortable: true }
      ],
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
#files-download {
  min-height: 100vh;
  display: flex;
  flex-direction: column;
}
</style>
