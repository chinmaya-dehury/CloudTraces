<template>
  <div id="upload-trace-file">
    <NavBar/>
    <b-container class="upload-file-container mt-5">
      <b-row class="text-center">
        <b-col/>
        <b-col cols="10" class="upload-form-col">
          <h1>Upload Trace File</h1>
          <div id="upload-form">
            <b-form @submit="onSubmit" v-if="show" class="form-check-inline">
              <b-form-group
                  id="input-group-1"
                  label="Provider:"
                  label-for="input-1"
                  description=""
                  class="mx-2 my-2"
              >
                <b-form-input
                    id="input-1"
                    v-model="form.provider"
                    :state="providerState"
                    placeholder="Enter provider"
                    aria-describedby="input-live-feedback"
                    trim
                    required
                />

                <b-form-invalid-feedback id="input-live-feedback">
                  Enter at least 3 letters
                </b-form-invalid-feedback>
              </b-form-group>

              <b-form-group
                  id="input-group-2"
                  label="Trace type:"
                  label-for="input-2"
                  class="mx-2 my-2"
              >
                <b-form-select
                    id="input-2"
                    v-model="form.traceType"
                    :options="traceTypes"
                    required
                />
              </b-form-group>

              <b-form-group
                  id="input-group-3"
                  label="File delimiter:"
                  label-for="input-3"
                  class="mx-2 my-2"
              >
                <b-form-select
                    id="input-3"
                    v-model="form.delimiter"
                    :options="delimiters"
                    required
                />
              </b-form-group>

              <b-form-group
                  id="input-group-4"
                  label="Trace file:"
                  label-for="input-4"
                  class="mx-2 my-2"
              >
                <b-form-file
                    id="input-4"
                    v-model="form.file"
                    accept="text/csv"
                    required
                    plain
                />
              </b-form-group>
              <b-button type="submit" variant="primary" class="mx-2 my-2">Upload</b-button>
            </b-form>
          </div>
        </b-col>
        <b-col/>
      </b-row>
    </b-container>
  </div>
</template>

<script>
import NavBar from '@/components/Navbar.vue';

export default {
  name: 'UploadFile',
  components: { NavBar },
  data() {
    return {
      form: {
        provider: '',
        traceType: null,
        delimiter: null,
        file: null,
      },
      traceTypes: [ {text: 'Select One', value: null}, 'SERVERLESS_PLATFORM', 'CLOUD_STORAGE', 'CLOUD_CLUSTER'],
      delimiters: [
        { text: 'Select One', value: null },
        'COMMA_SEPARATED',
        'SEMICOLON_SEPARATED',
        'TAB_SEPARATED',
        'PIPE_SEPARATED',
        'SPACE_SEPARATED'
      ],
      show: true
    }
  },
  computed: {
    providerState() {
      return this.form.provider.length > 2;
    }
  },
  methods: {
    onSubmit(event) {
      event.preventDefault();
    },
  }
}

</script>

<style scoped>
.upload-form-col {
  text-align: left;
}
</style>