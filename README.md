<h1>Power Analysis with PowerAnalysisApp</h1>

<div class="callout">
  <strong>Purpose:</strong> Estimate statistical power and required sample size for a given effect size distribution.
</div>

<h2>1. Introduction and Rationale</h2>
<p>
<strong>PowerAnalysisApp</strong> is a Java-based desktop application that helps researchers assess the statistical power of experimental designs. It is particularly useful in planning studies that compare groups using effect sizes (Cohen's <em>d</em>).
</p>
<p>
This app reads a CSV file of effect sizes, calculates key statistics like the median absolute Cohen’s <em>d</em>, and estimates the sample size needed to achieve a user-defined power level. A visual power curve is also generated.
</p>

<h2>2. Builhding and Installing</h2>
<details open>
  <summary>Getting PowerAnalysisApp</summary>
  <p>The easiest way to get <code>PowerAnalysisApp</code> is to download a pre-compiled version. You can download directly at <a href="https://github.com/DrATedder/PowerAnalysisApp/releases/tag/v0.1.1" target="_blank">Releases</a>.</p>
  
   <p>Alternatively, follow these simple steps:</p>
  <p>1. Clone the git repository</p>
  <pre><code>
   git clone https://github.com/DrATedder/PowerAnalysisApp.git
  </code></pre>
  <p>
   2. Navigate to the directory
  </p>
  <pre><code>
  cd PowerAnalysisApp
  </code></pre>
  <p>
   3. Compile the package using <code>maven</code>
  </p>
  <pre><code>
   mvn clean package
  </code></pre>
  <p>
   4. Navigate to the directory 
  </p>
  <pre><code>cd target</code></pre>
  <p>
   5. Run the package (alternatively, you can 'double click' if your system allows it)
  </p>
  <pre><code>
  java -jar PowerAnalysisApp-1.0.jar
 </code></pre>
</details>  

<h2>2. Input Data Format</h2>
<details open>
  <summary>Required CSV Structure</summary>
  <p>The input file must include a header row with the following columns:</p>
  <table>
    <thead>
      <tr><th>Column</th><th>Description</th></tr>
    </thead>
    <tbody>
      <tr><td><code>ProbeID</code></td><td>Unique identifier for each gene or feature</td></tr>
      <tr><td><code>Cohens_d</code></td><td>Effect size estimate (Cohen’s <em>d</em>)</td></tr>
    </tbody>
  </table>
</details>

<h2>3. Running the Application</h2>
<details open>
  <summary>Step 1: Launch the App</summary>
  <ul>
    <li>Double-click the <code>PowerAnalysisApp.jar</code> file (Java 8+ required).</li>
    <li>If it doesn’t start, try running from the command line:<br>
      <code>java -jar PowerAnalysisApp.jar</code></li>
  </ul>
</details>

<details>
  <summary>Step 2: Select Your CSV File</summary>
  <p>Click the <strong>Select CSV File</strong> button and choose your input file.</p>
</details>

<details>
  <summary>Step 3: Set Analysis Parameters</summary>
  <ul>
    <li><strong>Significance Level (alpha):</strong> e.g. 0.05</li>
    <li><strong>Desired Power:</strong> e.g. 0.8 (80%)</li>
    <li><strong>Group Ratio:</strong> e.g. 1.0 for equal-sized groups</li>
  </ul>
</details>

<details>
  <summary>Step 4: Run the Analysis</summary>
  <p>Click <strong>Run Analysis</strong> to perform the power calculation.</p>
</details>

<h2>4. Output and Interpretation</h2>
<p>Once the analysis completes, you will see:</p>
<ul>
  <li><strong>Median Cohen's <em>d</em>:</strong> Typical effect size in your dataset</li>
  <li><strong>Adjusted alpha:</strong> Alpha corrected for multiple comparisons (Bonferroni)</li>
  <li><strong>Estimated Sample Size:</strong> Number of subjects per group needed to achieve your desired power</li>
</ul>

<div class="highlight">
  The app uses absolute values of Cohen’s <em>d</em> and assumes a two-sample comparison with a t-test framework.
</div>

<h2>5. Power Curve Plot</h2>
<p>
After analysis, a plot window opens showing a curve of statistical power across a range of sample sizes.
</p>

<details open>
  <summary>Saving the Plot</summary>
  <ul>
    <li>Click <strong>Save Plot as PNG</strong> to export the chart for use in reports or publications.</li>
  </ul>
</details>

<h2>6. Best Practices</h2>
<table>
  <thead>
    <tr><th>Component</th><th>Recommendation</th></tr>
  </thead>
  <tbody>
    <tr><td>Effect Size Source</td><td>Use pilot data or prior literature</td></tr>
    <tr><td>Alpha</td><td>Start with 0.05, adjust for multiple testing if needed</td></tr>
    <tr><td>Power Threshold</td><td>Use ≥ 0.8 for most studies</td></tr>
    <tr><td>Group Ratio</td><td>Use 1.0 unless unequal sampling is expected</td></tr>
  </tbody>
</table>

<h2>7. Limitations</h2>
<ul>
  <li>Assumes normally distributed data</li>
  <li>Currently supports one effect size column only</li>
  <li>No support for paired or longitudinal designs</li>
</ul>

<h2>8. Getting Help</h2>
<ul>
  <li>For issues or suggestions, open a GitHub issue at:<br>
    <a href="https://github.com/DrATedder/PowerAnalysisApp/issues" target="_blank">
      https://github.com/DrATedder/PowerAnalysisApp/issues</a></li>
</ul>

<h2>9. Credits</h2>
<p>
This tool was developed and maintained by <a href="https://github.com/DrATedder" target="_blank">DrATedder</a>. Contributions welcome!
</p>

</body>
</html>
