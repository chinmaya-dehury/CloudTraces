<template>
  <div id="footer" class="container-fluid text-center bg-light p-3 ">
    <b-container class="center-content">
      <b-row>
        <b-col/>
        <b-col cols="10">
          <div id="footer-content" class="d-inline-block">
            <p>Edgar Selihov</p>
            <p>2022</p>
            <p class="h4">
              <b-link href="https://github.com/chinmaya-dehury/CloudTraces" target="_blank" active secondary>
                <b-icon icon="github" style="color:black"/>
              </b-link>
            </p>
          </div>
        </b-col>
        <b-col/>
      </b-row>
    </b-container>
  </div>
</template>

<script>
export default {
  // eslint-disable-next-line vue/multi-word-component-names
  name: 'Footer',
}
</script>

<style scoped>
  body, #footer {
    display: flex;
    flex-direction: column;
  }

  #footer-content {
    flex-grow: 1;
  }
</style>