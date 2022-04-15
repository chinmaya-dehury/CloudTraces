<template>
  <div id="upload-trace-file">
    <NavBar/>
    <b-container class="bv-example-row">
      <b-row class="text-center">
        <b-col></b-col>
        <b-col cols="10">
          <h1>Upload Trace Files</h1>
          <div id="upload-form">
            <b-form @submit="onSubmit" @reset="onReset" v-if="show">
              <b-form-group
                  id="input-group-1"
                  label="Provider:"
                  label-for="input-1"
                  description=""
              >
                <b-form-input
                    id="input-1"
                    v-model="form.provider"
                    placeholder="Enter provider"
                    required
                ></b-form-input>
              </b-form-group>

              <b-form-group
                  id="input-group-2"
                  label="Trace type:"
                  label-for="input-2"
              >
                <b-form-select
                    id="input-2"
                    v-model="form.traceType"
                    :options="traceTypes"
                    required
                ></b-form-select>
              </b-form-group>

              <b-form-group
                  id="input-group-3"
                  label="File delimiter:"
                  label-for="input-3"
              >
                <b-form-select
                    id="input-3"
                    v-model="form.delimiter"
                    :options="delimiters"
                    required
                ></b-form-select>
              </b-form-group>

              <b-button type="submit" variant="primary">Submit</b-button>
              <b-button type="reset" variant="warning">Clear</b-button>
            </b-form>
          </div>
        </b-col>
        <b-col></b-col>
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
      },
      traceTypes: [ { text: 'Select One', value: null }, 'SERVERLESS_PLATFORM', 'CLOUD_STORAGE', 'CLOUD_CLUSTER'],
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
  methods: {
    onSubmit(event) {
      event.preventDefault();
    },
    onReset(event) {
        event.preventDefault();
        this.form.provider = '';
        this.form.traceType = '';
        this.form.delimiter = '';
        this.show = false;
        this.$nextTick(() => {
          this.show = true;
        });
    },
  }
}

</script>

<style scoped>

</style>