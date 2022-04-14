<template>
    <b-navbar toggleable="lg" type="light" variant="warning">
      <div id="inner" class="w-100">
        <b-container class="center-content">
          <b-row class="text-center">
            <b-col></b-col>
            <b-col cols="10" class="d-flex w-100 justify-content-between">
              <b-navbar-brand href="#">CTB</b-navbar-brand>
              <b-navbar-toggle class="toggle-btn" target="nav-collapse"></b-navbar-toggle>

              <div class="collapsed-navbar-el">
                <b-collapse id="nav-collapse" is-nav>
                  <b-navbar-nav class="ml-auto">
                    <b-nav-item-dropdown text="Files">
                      <b-dropdown-item href="#">Upload Trace Files</b-dropdown-item>
                      <b-dropdown-item href="#">Download Trace Files</b-dropdown-item>
                    </b-nav-item-dropdown>
                    <b-nav-item href="#">Home</b-nav-item>
                    <b-nav-item href="#">About</b-nav-item>
                  </b-navbar-nav>
                </b-collapse>
              </div>
            </b-col>
            <b-col></b-col>
          </b-row>
        </b-container>
      </div>
    </b-navbar>
</template>

<script>
export default {
  name: 'Nav-bar',
}
</script>

<style scoped>
.toggle-btn {
  margin-left: auto;
}

.collapsed-navbar-el {
  margin-left: 2%;
}

</style>