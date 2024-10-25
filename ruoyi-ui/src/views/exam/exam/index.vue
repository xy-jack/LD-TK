<template>

  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="开放类型" prop="openType">
        <el-select v-model="queryParams.openType" class="filter-item" clearable>
          <el-option
            v-for="item in openTypes"
            :key="item.value"
            :label="item.label"
            :value="item.value"
          />
        </el-select>
      </el-form-item>

      <el-form-item label="考试时间" prop="startTime">
        <el-date-picker
          v-model="queryParams.startTime"
          class="filter-item"
          value-format="yyyy-MM-dd"
          type="date"
          placeholder="考试开始时间"
        />
        -
        <el-date-picker
          v-model="queryParams.endTime"
          class="filter-item"
          value-format="yyyy-MM-dd"
          type="date"
          placeholder="考试结束时间"
        />
      </el-form-item>


      <el-form-item label="考试名称" prop="title">
        <el-input v-model="queryParams.title" placeholder="搜索考试名称" style="width: 200px;" class="filter-item" />
      </el-form-item>

      <el-form-item>
        <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
        <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
      </el-form-item>
    </el-form>

    <el-row :gutter="10" class="mb8">
      <el-col :span="1.5">
        <el-button
          type="primary"
          plain
          icon="el-icon-plus"
          size="mini"
          @click="handleAdd"
          v-hasPermi="['system:repo:add']"
        >新增</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="success"
          plain
          icon="el-icon-edit"
          size="mini"
          :disabled="single"
          @click="handleUpdate"
          v-hasPermi="['system:repo:edit']"
        >修改</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="danger"
          plain
          icon="el-icon-delete"
          size="mini"
          :disabled="multiple"
          @click="handleDelete"
          v-hasPermi="['system:repo:remove']"
        >删除</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="dataList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />

      <el-table-column label="考试名称" prop="title"/>

      <el-table-column label="考试类型" align="center">
        <template v-slot="scope">
          <dict-tag :options="dict.type.t_exam_open_type" :value="scope.row.openType"/>
        </template>
      </el-table-column>

      <el-table-column label="考试时间" width="220px" align="center">
        <template v-slot="scope">
          <span v-if="scope.row.timeLimit">
            {{ scope.row.startTime }} ~ {{ scope.row.endTime }}
          </span>
          <span v-else>不限时</span>
        </template>
      </el-table-column>

      <el-table-column label="考试总分" prop="totalScore" align="center"/>

      <el-table-column label="及格线" prop="qualifyScore" align="center"/>

      <el-table-column label="状态" align="center">
        <template v-slot="scope">
          <dict-tag :options="dict.type.sys_normal_disable" :value="scope.row.state"/>
        </template>
      </el-table-column>

      <el-table-column label="操作" align="center" width="220px">
        <template v-slot="scope">
          <el-button type="primary" size="mini" icon="el-icon-edit" @click="handleUpdateExam(scope.row.id)">修改</el-button>
          <el-button type="warning" size="mini" icon="el-icon-user" @click="handleExamDetail(scope.row.id)">考试详情</el-button>
        </template>
      </el-table-column>

      <pagination
        v-show="total>0"
        :total="total"
        :page.sync="queryParams.pageNum"
        :limit.sync="queryParams.pageSize"
        @pagination="getList"
      />
    </el-table>

  </div>

</template>

<script>
import {listRepo, getRepo, delRepo, saveOrUpdateRepo} from "@/api/exam/exam";

export default {
  name: 'ExamList',
  dicts: ['sys_normal_disable','t_exam_open_type'],
  data() {
    return {
      // 遮罩层
      loading: true,
      // 显示搜索条件
      showSearch: true,
      // 总条数
      total: 0,
      // 列表展示数据
      dataList: [],
      // 非单个禁用
      single: true,
      // 非多个禁用
      multiple: true,
      openTypes: [
        {
          value: 1,
          label: '完全开放'
        },
        {
          value: 2,
          label: '指定部门'
        }
      ],

      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        title: null,
      },

      options: {
        // 可批量操作
        multi: true,
        // 批量操作列表
        multiActions: [
          {
            value: 'delete',
            label: '删除'
          }, {
            value: 'enable',
            label: '启用'
          },
          {
            value: 'disable',
            label: '禁用'
          }
        ],
        // 列表请求URL
        //listUrl: '/exam/api/exam/exam/paging',
        // 删除请求URL
        //deleteUrl: '/exam/api/exam/exam/delete',
        // 删除请求URL
        //stateUrl: '/exam/api/exam/exam/state',
        addRoute: 'AddExam'
      }
    }
  },
  created() {
    this.getList();
  },
  methods: {
    /** 查询题库信息列表 */
    getList() {
      this.loading = true;
      listRepo(this.queryParams).then(response => {
        this.dataList = response.rows;
        this.total = response.total;
        this.loading = false;
      });
    },

    handleExamDetail(examId) {
      this.$router.push({ name: 'ListExamUser', params: { examId: examId }})
    },

    handleAdd() {
      this.$router.push({ name: 'UpdateExam', params: {}})
    },

    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const id = row.id || this.ids
      getRepo(id).then(response => {
        this.form = response.data;
        this.open = true;
        this.title = "修改试题信息";
      });
    },

    /** 删除按钮操作 */
    handleDelete(row) {
      const ids = this.ids;
      if (row.id) {
        ids.push(row.id)
      }
      this.$modal.confirm('是否确认删除题库信息编号为"' + ids + '"的数据项？').then(function() {
        return delRepo(ids);
      }).then(() => {
        this.getList();
        this.$modal.msgSuccess("删除成功");
      }).catch(() => {});
    },

    handleUpdateExam(examId) {
      this.$router.push("/exam/exam-update/exam/update/" + examId);
      //this.$router.push({ name: 'UpdateExam', params: { examId: examId }})
    },

    /** 搜索按钮操作 */
    handleQuery() {
      this.queryParams.pageNum = 1;
      this.getList();
    },

    /** 重置按钮操作 */
    resetQuery() {
      this.resetForm("queryForm");
      this.handleQuery();
    },

    // 多选框选中数据
    handleSelectionChange(selection) {
      this.ids = selection.map(item => item.id)
      this.single = selection.length!==1
      this.multiple = !selection.length
    },
  }
}
</script>
