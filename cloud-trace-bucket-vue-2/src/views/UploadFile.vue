<template>
  <div id="upload-trace-file" class="font-monospace">
    <NavBar/>
    <b-container class="mt-5">
      <b-row>
        <b-col/>
        <b-col cols="10">
          <StatusAlert
              ref="statusResponseAlert"
              :message="apiResponse.message"
              :response-status="apiResponse.status"
          />
          <h1>Upload Trace File</h1>
          <p>
            Provide CSV trace file to be processed.
            <br>
            For more trace type info see <b-link :to="{ name: 'About' }">About</b-link> page.
          </p>
          <div id="upload-form">
            <b-form @submit="onSubmit" class="form-check-inline">
              <b-form-group
                  id="input-group-1"
                  label="Provider"
                  label-for="input-1"
                  class="my-2"
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
                  Provider must be 3-20 characters long
                </b-form-invalid-feedback>
              </b-form-group>

              <b-form-group
                  id="input-group-2"
                  label="Trace type"
                  label-for="input-2"
                  class="my-2"
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
                  label="File delimiter"
                  label-for="input-3"
                  class="my-2"
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
                  label="Trace file"
                  label-for="input-4"
                  class="my-2"
              >
                <b-form-file
                    id="input-4"
                    v-model="form.file"
                    accept="text/csv"
                    class="w-100"
                    required
                    plain
                />
              </b-form-group>

              <b-overlay
                  :show="isBusyUploading"
                  rounded
                  opacity="0.6"
                  spinner-small
                  spinner-variant="primary"
                  class="d-inline-block"
                  @hidden="onHidden"
              >
                <b-button
                    ref="button"
                    :disabled="!isFormFilled(this.form)"
                    type="submit"
                    variant="primary"
                    class="my-4"
                >
                  Upload
                </b-button>
              </b-overlay>
            </b-form>

            <!-- Form tooltips -->
            <b-tooltip
                target="input-1"
                triggers="hover"
                :title=this.tooltipText.provider
                placement="right"
                class="mx-2"
            />
            <b-tooltip
                target="input-2"
                triggers="hover"
                :title=this.tooltipText.traceType
                placement="right"
                class="mx-2"
            />
            <b-tooltip
                target="input-3"
                triggers="hover"
                :title=this.tooltipText.delimiter
                placement="right"
                class="mx-2"
            />
            <b-tooltip
                target="input-4"
                triggers="hover"
                :title=this.tooltipText.file
                placement="right"
                class="mx-2"
            />
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
import StatusAlert from '@/components/alerts/StatusAlert';
import { uploadTraceFile } from '@/apiClient/storageApi';

export default {
  name: 'UploadFile',
  components: { StatusAlert, NavBar, Footer },
  data() {
    return {
      apiResponse: {
        message: null,
        status: null,
      },
      form: {
        provider: '',
        traceType: null,
        delimiter: null,
        file: null,
      },
      tooltipText: {
        provider: 'Specify trace file provider name (e.g Google)',
        traceType: "Specify file's trace type",
        delimiter: 'Specify .csv file delimiter',
        file: 'CSV file only'
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
      isBusyUploading: false,
    }
  },
  computed: {
    providerState() {
      return this.form.provider.length > 2 && this.form.provider.length <= 20;
    },
  },
  methods: {
    async onSubmit(event) {
      event.preventDefault();

      this.isBusyUploading = true;
      this.apiResponse = await uploadTraceFile(this.form);

      if (this.apiResponse) {
        this.isBusyUploading = false;
        this.$refs.statusResponseAlert.triggerAlert();
        this.clearForm();
      }

      await this.$nextTick();
    },

    onHidden() {
      this.$refs.button.focus();
    },

    isFormFilled(form) {
      return form.provider && form.traceType && form.delimiter && form.file;
    },

    clearForm() {
      this.form.provider = '';
      this.form.traceType = null;
      this.form.delimiter = null;
      this.form.file = null;
    }
  }
}

</script>

<style scoped>
  p {
    color: #6c757d;
  }
  #upload-form {
    min-height: 73vh;
    display: flex;
    flex-direction: row;
  }
</style>
