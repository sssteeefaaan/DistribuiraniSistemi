
namespace WindowsFormsAppExactTime
{
    partial class FormExactTime
    {
        /// <summary>
        /// Required designer variable.
        /// </summary>
        private System.ComponentModel.IContainer components = null;

        /// <summary>
        /// Clean up any resources being used.
        /// </summary>
        /// <param name="disposing">true if managed resources should be disposed; otherwise, false.</param>
        protected override void Dispose(bool disposing)
        {
            if (disposing && (components != null))
            {
                components.Dispose();
            }
            base.Dispose(disposing);
        }

        #region Windows Form Designer generated code

        /// <summary>
        /// Required method for Designer support - do not modify
        /// the contents of this method with the code editor.
        /// </summary>
        private void InitializeComponent()
        {
            this.buttonExactTime = new System.Windows.Forms.Button();
            this.labelExactTime = new System.Windows.Forms.Label();
            this.SuspendLayout();
            // 
            // buttonExactTime
            // 
            this.buttonExactTime.Location = new System.Drawing.Point(89, 95);
            this.buttonExactTime.Name = "buttonExactTime";
            this.buttonExactTime.Size = new System.Drawing.Size(144, 49);
            this.buttonExactTime.TabIndex = 0;
            this.buttonExactTime.Text = "Get exact time";
            this.buttonExactTime.UseVisualStyleBackColor = true;
            this.buttonExactTime.Click += new System.EventHandler(this.buttonExactTime_Click);
            // 
            // labelExactTime
            // 
            this.labelExactTime.AutoSize = true;
            this.labelExactTime.Location = new System.Drawing.Point(152, 57);
            this.labelExactTime.Name = "labelExactTime";
            this.labelExactTime.Size = new System.Drawing.Size(0, 19);
            this.labelExactTime.TabIndex = 1;
            // 
            // FormExactTime
            // 
            this.AutoScaleDimensions = new System.Drawing.SizeF(9F, 19F);
            this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.Font;
            this.ClientSize = new System.Drawing.Size(359, 178);
            this.Controls.Add(this.labelExactTime);
            this.Controls.Add(this.buttonExactTime);
            this.Font = new System.Drawing.Font("Times New Roman", 12F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.Margin = new System.Windows.Forms.Padding(4, 4, 4, 4);
            this.Name = "FormExactTime";
            this.Text = "Exact time";
            this.MaximumSizeChanged += new System.EventHandler(this.FormExactTime_ResizeEnd);
            this.MinimumSizeChanged += new System.EventHandler(this.FormExactTime_ResizeEnd);
            this.Load += new System.EventHandler(this.FormExactTime_Load);
            this.ResizeBegin += new System.EventHandler(this.FormExactTime_ResizeEnd);
            this.ResizeEnd += new System.EventHandler(this.FormExactTime_ResizeEnd);
            this.ResumeLayout(false);
            this.PerformLayout();

        }

        #endregion

        private System.Windows.Forms.Button buttonExactTime;
        private System.Windows.Forms.Label labelExactTime;
    }
}

