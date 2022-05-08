<template>
  <div id="about-page" class="font-monospace">
    <NavBar/>
    <b-container class="mt-5">
      <b-row>
        <b-col/>
        <b-col cols="10">
          <div id="about-content">
            <h1>About CloudTraceBucket</h1>
            <div class="my-5">
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
              <div class="my-5">
                <h3 class="pb-2 fw-bold">What are Trace Data Types?</h3>
                <h5>
                  Trace Data Type is a categorisation of the system where the trace data comes from.
                  Trace type defines what generalized table will be used for inserting processed cloud trace data.
                  <b>CloudTraceBucket</b> uses next cloud trace types:
                </h5>
                <div class="mt-4">
                  <ul>
                    <li>
                      <b><i>SERVERLESS_PLATFORM</i></b> - is a cloud-native development model that allows developers
                      to build
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
              </div>
              <div class="mb-5">
                <h3 class="pb-2 fw-bold">What are generalized tables?</h3>
                <h5>
                  <b>Generalized tables</b> are a result of manual analysis of publicly available cloud traces. The
                  cloud traces provided by Alibaba, TU Delft and Microsoft Azure were grouped by cloud
                  trace type. After the cloud trace groups were formed, cloud trace files were inspected for
                  columns, which have a common column name. All uploaded trace files’ data eventually
                  will be inserted into generalized tables:
                </h5>
                <div class="mt-4">
                  <ul>
                    <li>
                      <b><i>serverless_platform</i></b> – holds information about allocated memory, function invocation
                      count, and the time when the function was invoked
                      <div class="mt-4 serverless-platform">
                        <b-table bordered striped hover :items="serverlessPlatform"></b-table>
                      </div>
                    </li>
                    <li class="pt-3">
                      <b><i>cloud_storage</i></b> - holds information about cloud storage read/write operations,
                      what blob type the data is and how many bytes were requested
                      <div class="mt-4 cloud-storage">
                        <b-table bordered striped hover :items="cloudStorage"></b-table>
                      </div>
                    </li>
                    <li class="pt-3">
                      <b><i>cloud_cluster</i></b> - holds information about nodes of virtual
                      machines, CPU number, disk space, memory requested, what event type is (create,
                      remove)
                      <div class="mt-4 cloud-storage">
                        <b-table bordered striped hover :items="cloudStorage"></b-table>
                      </div>
                    </li>
                  </ul>
                </div>
              </div>
              <div class="my-5">
                <h3 class="pb-2 fw-bold">Currently supported browsers</h3>
                <div>
                  <h5>
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
                  </h5>
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
  data() {
    return {
      serverlessPlatform: [
        { column_name: 'function_name', description: 'Unique name of the function invoked', data_type: 'VARCHAR(255)' },
        { column_name: 'count', description: 'Number of times the function was executed', data_type: 'BIGINT' },
        { column_name: 'allocated_memory_mb', description: 'Number of allocated memory for the invoked function', data_type: 'BIGINT' },
        { column_name: 'time', description: 'Time when the function was invoked', data_type: 'VARCHAR(255)' },
        { column_name: 'provider', description: 'The owner of the data', data_type: 'VARCHAR(255)' },
      ],
      cloudStorage: [
        { column_name: 'read', description: 'Describes if data was read', data_type: 'BOOLEAN' },
        { column_name: 'write', description: 'Describes if data was written', data_type: 'BOOLEAN' },
        { column_name: 'blob_type', description: 'Describes what blob type was in action', data_type: 'VARCHAR(255)' },
        { column_name: 'blob_bytes', description: 'How many bytes were either read/written from/to blob', data_type: 'VARCHAR(255)' },
        { column_name: 'provider', description: 'The owner of the data', data_type: 'VARCHAR(255)' },
      ],
      cloudCluster: [
        { column_name: 'plan_cpu', description: 'Number of CPU requested for cluster', data_type: 'INT' },
        { column_name: 'plan_disk', description: 'Normalized disk space requested for cluster', data_type: 'BOOLEAN' },
        { column_name: 'event_type', description: 'Event type of the cluster (create/remove)', data_type: 'VARCHAR(255)' },
        { column_name: 'provider', description: 'The owner of the data', data_type: 'VARCHAR(255)' },
      ],
    }
  }
}
</script>

<style scoped>
#about-content {
  min-height: 82vh;
  display: flex;
  flex-direction: column;
}
</style>
