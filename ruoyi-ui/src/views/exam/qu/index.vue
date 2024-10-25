<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="题目类型 " prop="quType">
        <el-select v-model="queryParams.quType" class="filter-item" clearable>
          <el-option
            v-for="item in quTypes"
            :key="item.value"
            :label="item.label"
            :value="item.value"
          />
        </el-select>
      </el-form-item>

      <el-form-item label="归属题库" prop="repoIds">
        <repo-select v-model="queryParams.repoIds" :multi="true" />
      </el-form-item>

      <el-form-item label="题库名称" prop="title">
        <el-input
          v-model="queryParams.content"
          placeholder="请输入题目内容"
          clearable
          @keyup.enter.native="handleQuery"
        />
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
<!--      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="el-icon-download"
          size="mini"
          @click="handleExport"
          v-hasPermi="['system:repo:export']"
        >导出</el-button>
      </el-col>-->
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="repoList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="题目类型" align="center" width="100px">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.t_qu_type" :value="scope.row.quType"/>
        </template>
      </el-table-column>
      <el-table-column label="题目内容" align="center" prop="content"/>
      <el-table-column label="创建时间" align="center" prop="createTime"  width="180px"/>
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width"  width="160">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['system:repo:edit']"
          >修改</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['system:repo:remove']"
          >删除</el-button>
        </template>
      </el-table-column>
    </el-table>

    <pagination
      v-show="total>0"
      :total="total"
      :page.sync="queryParams.pageNum"
      :limit.sync="queryParams.pageSize"
      @pagination="getList"
    />

    <!-- 添加或修改题库信息对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="1000px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">

        <el-form-item label="题目类型" prop="quType">
          <el-select v-model="form.quType" :disabled="quTypeDisabled" class="filter-item" @change="handleTypeChange">
            <el-option
              v-for="item in quTypes"
              :key="item.value"
              :label="item.label"
              :value="item.value"
            />
          </el-select>
        </el-form-item>

        <el-form-item label="难度等级 " prop="level">
          <el-select v-model="form.level" class="filter-item">
            <el-option
              v-for="item in levels"
              :key="item.value"
              :label="item.label"
              :value="item.value"
            />
          </el-select>
        </el-form-item>

        <el-form-item label="归属题库" prop="repoIds">
          <repo-select v-model="form.repoIds" :multi="true" />
        </el-form-item>

        <el-form-item label="题目内容" prop="content">
          <el-input v-model="form.content" type="textarea" />
        </el-form-item>

        <el-form-item label="试题图片">
          <file-upload v-model="form.image" accept=".jpg,.jepg,.png" />
        </el-form-item>

        <el-form-item label="整题解析" prop="oriPrice">
          <el-input v-model="form.analysis" :precision="1" :max="999999" type="textarea" />
        </el-form-item>
      </el-form>

      <div v-if="form.quType!==4" class="filter-container" style="margin-top: 25px">
        <el-button class="filter-item" type="primary" icon="el-icon-plus" size="small" plain @click="handleAddLine">添加</el-button>

        <el-table
          :data="form.answerList"
          :border="true"
          style="width: 100%;"
        >
          <el-table-column
            label="是否答案"
            width="120"
            align="center"
          >
            <template v-slot="scope">

              <el-checkbox v-model="scope.row.isRight">答案</el-checkbox>

            </template>

          </el-table-column>

          <el-table-column
            v-if="itemImage"
            label="选项图片"
            width="120px"
            align="center"
          >
            <template v-slot="scope">

              <file-upload
                v-model="scope.row.image"
                accept=".jpg,.jepg,.png"
              />

            </template>
          </el-table-column>

          <el-table-column
            label="答案内容"
          >
            <template v-slot="scope">
              <el-input v-model="scope.row.content" type="textarea" />
            </template>
          </el-table-column>

          <el-table-column
            label="答案解析"
          >
            <template v-slot="scope">
              <el-input v-model="scope.row.analysis" type="textarea" />
            </template>
          </el-table-column>

          <el-table-column
            label="操作"
            align="center"
            width="100px"
          >
            <template v-slot="scope">
              <el-button type="danger" icon="el-icon-delete" circle @click="removeItem(scope.$index)" />
            </template>
          </el-table-column>
        </el-table>
      </div>

      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitForm">确 定</el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import {listRepo, getRepo, delRepo, saveOrUpdateRepo} from "@/api/exam/qu";
import RepoSelect from '@/components/RepoSelect'

export default {
  name: "Repo",
  dicts: ['t_qu_type'],
  components: { RepoSelect },
  data() {
    return {
      quTypeDisabled: false,
      itemImage: true,
      // 遮罩层
      loading: true,
      // 选中数组
      ids: [],
      // 非单个禁用
      single: true,
      // 非多个禁用
      multiple: true,
      // 显示搜索条件
      showSearch: true,
      // 总条数
      total: 0,
      // 题库信息表格数据
      repoList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        quType: '',
        repoIds: [],
        content: '',
      },
      levels: [
        { value: 1, label: '普通' },
        { value: 2, label: '较难' }
      ],
      quTypes: [{
        value: 1,
        label: '单选题'
      }, {
        value: 2,
        label: '多选题'
      },
        {
          value: 3,
          label: '判断题'
        }
      ],
      // 表单参数
      form: {
        repoIds: [],
        tagList: [],
        answerList: []
      },
      // 表单校验
      rules: {
        content: [
          { required: true, message: '题目内容不能为空！' }
        ],

        quType: [
          { required: true, message: '题目类型不能为空！' }
        ],

        level: [
          { required: true, message: '必须选择难度等级！' }
        ],

        repoIds: [
          { required: true, message: '至少要选择一个题库！' }
        ]
      }
    };
  },
  created() {
    this.getList();
  },
  methods: {
    /** 查询题库信息列表 */
    getList() {
      this.loading = true;
      listRepo({pageNum: this.queryParams.pageNum, pageSize: this.queryParams.pageSize}, this.queryParams).then(response => {
        this.repoList = response.rows;
        this.total = response.total;
        this.loading = false;
      });
    },
    // 取消按钮
    cancel() {
      this.open = false;
      this.reset();
    },
    // 表单重置
    reset() {
      this.form = {
        id: null,
        level: null,
        quType: null,
        content: null,
        remark: null,
        analysis: null,
        createTime: null,
        updateTime: null
      };
      this.resetForm("form");
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
    /** 新增按钮操作 */
    handleAdd() {
      this.reset();
      this.open = true;
      this.title = "添加试题信息";
    },
    /*新增行数据*/
    handleAddLine() {
      this.form.answerList.push({ isRight: false, content: '', analysis: '' })
    },
    removeItem(index) {
      this.form.answerList.splice(index, 1)
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
    /** 提交按钮 */
    submitForm() {
      console.log(JSON.stringify(this.form))

      let rightCount = 0

      this.form.answerList.forEach(function(item) {
        if (item.isRight) {
          rightCount += 1
        }
      })

      if (this.form.quType === 1) {
        if (rightCount !== 1) {
          this.$message({
            message: '单选题答案只能有一个',
            type: 'warning'
          })

          return
        }
      }

      if (this.form.quType === 2) {
        if (rightCount < 2) {
          this.$message({
            message: '多选题至少要有两个正确答案！',
            type: 'warning'
          })

          return
        }
      }

      if (this.form.quType === 3) {
        if (rightCount !== 1) {
          this.$message({
            message: '判断题只能有一个正确项！',
            type: 'warning'
          })

          return
        }
      }

      this.$refs["form"].validate(valid => {
        if (valid) {
            saveOrUpdateRepo(this.form).then(response => {
              this.$modal.msgSuccess("新增成功");
              this.open = false;
              this.getList();
            });
        }
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
    /** 导出按钮操作 */
    handleExport() {
      this.download('/exam/api/qu/export', {
        ...this.queryParams
      }, `repo_${new Date().getTime()}.xlsx`)
    },
    handleTypeChange(v) {
      this.form.answerList = []
      if (v === 3) {
        this.form.answerList.push({ isRight: true, content: '正确', analysis: '' })
        this.form.answerList.push({ isRight: false, content: '错误', analysis: '' })
      }

      if (v === 1 || v === 2) {
        this.form.answerList.push({ isRight: false, content: '', analysis: '' })
        this.form.answerList.push({ isRight: false, content: '', analysis: '' })
        this.form.answerList.push({ isRight: false, content: '', analysis: '' })
        this.form.answerList.push({ isRight: false, content: '', analysis: '' })
      }
    },
  }
};
</script>
