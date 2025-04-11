# Wrangler Enhancement: ByteSize and TimeDuration Support

## aggregate-stats Directive

**Usage:**
```
aggregate-stats :size :time total_size_mb total_time_sec
```

**Example Input:**
| size   | time |
|--------|------|
| 1MB    | 1s   |
| 512KB  | 500ms |

**Output:**
| total_size_mb | total_time_sec |
|----------------|----------------|
| 1.5            | 1.5            |